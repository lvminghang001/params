package org.params.guomei;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.params.guomei.constants.GmCommonConsts;
import org.params.guomei.dto.GmData;
import org.params.guomei.dto.GmReqHead;
import org.params.guomei.dto.GmResp;
import org.params.guomei.utils.DemoSignUtil;
import org.params.yql.utils.CommonUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Map;
import java.util.Set;

import static org.params.guomei.utils.DemoSignUtil.*;

@RestController
@RequestMapping(value = "/guoMei")
public class GuoMeiController {


    //国美制造撞库参数
    @GetMapping(value = "/hitParams")
    public String hitParams(String phone,String customerName,String cardNo) throws Exception {
        JSONObject object=new JSONObject();
        JSONObject reqData=new JSONObject();
        reqData.put("customerName", CommonUtils.safeMd5(customerName));
        reqData.put("idNo", CommonUtils.safeMd5(cardNo));
        reqData.put("phoneNo",CommonUtils.safeMd5(phone));
        JSONObject reqHead=new JSONObject();
        reqHead.put("channeId","8520369");
        object.put("reqData",reqData);
        object.put("reqHead",reqHead);
        RequestParams requestParams=encryptReqMsg(object.toJSONString(),"c7a1e5f2b3d2a8f5",GmCommonConsts.mytestPrivateKey,GmCommonConsts.mytestPublicKey);
        return JSON.toJSONString(requestParams);
    }

    GmResp doResp(JSONObject data, String errStatus, String message){
        Boolean isPro=Boolean.FALSE;
        String enStr=null;
        if(GmCommonConsts.failStatus.equals(errStatus)){
            GmData<JSONObject> dataRaw = new GmData<>(null);
            dataRaw.setStatus(GmCommonConsts.failStatus);
            dataRaw.setMessage(message);
            enStr = getJsonStringWrapperData(dataRaw);
        }else{
            GmData<JSONObject> dataRaw = new GmData<>(data);
            enStr=getJsonStringWrapperData(dataRaw);
        }
       // log.info("给国美的明文业务数据:{}", enStr);
        // 4.  加密整个data 作为respData
        String random = DemoSignUtil.getRandom();
        String encryptRandomKey = null;
        try {
            encryptRandomKey = encryptByPublicKey4Pkcs5(random.getBytes(Charset.forName("UTF-8")),getGuoMPubKey(isPro));
        } catch (Exception e) {
       //     log.error(e.getMessage());
        }
        // 加密给国美的真正data业务报文数据
        String respData = encrypt4Base64(enStr, random);
        // 5. 签名 【重要】 如果调试有问题，可以把data  自己的privateKey  国美侧的publicKey 打印出来
       // log.info("签名的data:{} 国美的publicKey:{}, 合作方自己的 privateKey:{}",data,getGuoMPubKey(isPro),getMyPriKey(isPro));
        String signToGome = null;
        try {
            signToGome = sign(enStr.getBytes(Charset.forName("UTF-8")), getMyPriKey(isPro));
        } catch (Exception e) {
           // log.error(e.getMessage());
        }
        GmReqHead reqHead = new GmReqHead(encryptRandomKey,signToGome);
        // 6. 构建响应
       // log.info("最终给国美响应的数据报是:{}",JSONObject.toJSONString(new GmResp(reqHead,respData)));
        return  new GmResp(reqHead,respData);
    }

    public static String getJsonStringWrapperData(GmData<JSONObject> data) {
        return JSONObject.toJSONString(data);
    }

    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes =java.util.Base64.getDecoder().decode(privateKey.getBytes(StandardCharsets.UTF_8));
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return new String(java.util.Base64.getEncoder().encode(signature.sign()));
    }


    public static String getGuoMPubKey(Boolean isPro){
        if(isPro){
            return GmCommonConsts.gomeProPublicKey;
        }
        return GmCommonConsts.gometestPublicKey;
    }

    public static String getMyPubKey(Boolean isPro){
        if(isPro){
            return GmCommonConsts.myProPublicKey;
        }
        return GmCommonConsts.mytestPublicKey;
    }


    public static String getMyPriKey(Boolean isPro){
        if(isPro){
            return GmCommonConsts.myProPrivateKey;
        }
        return GmCommonConsts.mytestPrivateKey;
    }


    /**
     * 加密内容
     *
     * @param reqData
     * @param key
     * @param privateKey
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static RequestParams encryptReqMsg(String reqData, String key, String privateKey, String publicKey) throws Exception {
        // 请求报文按照默认顺序转json
        JSONObject reqJson = JSON.parseObject(reqData, Feature.OrderedField);

        RequestParams requestParams = new RequestParams();
        // 获取请求体
        JSONObject reqDataJson = reqJson.getJSONObject("reqData");
        JSONObject reqHeadJson = reqJson.getJSONObject("reqHead");

        //RSA签名：使用私钥对请求体业务字段进行加签
        String sign = sign(reqDataJson.toJSONString().getBytes(Charset.forName("UTF-8")), privateKey);

        requestParams.getHeaders().put("signData", sign);
        // AES加密请求数据，用16位随机密钥key明文加密请求体reqData
        String AESreqData = encrypt4Base64(reqDataJson.toString(), key);
        requestParams.setBody(AESreqData);
        // 使用公钥加密16位随机密钥key
        String randomKey = encryptByPublicKey4Pkcs5(key.getBytes(Charset.forName("UTF-8")), publicKey);
        requestParams.getHeaders().put("randomKey", randomKey);

        Set<Map.Entry<String, Object>> entries = reqHeadJson.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            requestParams.getHeaders().put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        // 最终请求报文
        return requestParams;
    }

    public static void main(String[] args) throws Exception{
        JSONObject object=new JSONObject();
        JSONObject reqData=new JSONObject();
        reqData.put("phone","18870936109");
        JSONObject reqHead=new JSONObject();
        reqHead.put("channeId","8520369");
        object.put("reqData",reqData);
        object.put("reqHead",reqHead);
        RequestParams requestParams=encryptReqMsg(object.toJSONString(),"c7a1e5f2b3d2a8f5",GmCommonConsts.mytestPrivateKey,GmCommonConsts.mytestPublicKey);
        System.out.println(JSON.toJSONString(requestParams.getHeaders()));
    }

}
