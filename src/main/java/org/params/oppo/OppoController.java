package org.params.oppo;

import com.alibaba.fastjson.JSON;
import org.params.common.utils.AESKeyGenerator;
import org.params.oppo.dto.ChannelStandardReqDto;
import org.params.oppo.util.AesNewUtil;
import org.params.oppo.util.OppoConstant;
import org.params.oppo.util.OppoSignatureUtils;
import org.params.oppo.util.RsaHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 *   oppo联登渠道参数制造
 */
@RestController
@RequestMapping(value = "/oppo")
public class OppoController {

    public static void main(String[] args) throws Exception{
        System.out.println(OppoSignatureUtils.getPublicKey(OppoConstant.ryhPubKey));
        AESKeyGenerator aesKeyGenerator=new AESKeyGenerator();
        System.out.println(new String(aesKeyGenerator.getSecretKey().getEncoded()));
        String aesKey="MEqLCnG2Q0IfauMD";
        String key= RsaHelper.encryptByPublicKey(aesKey,OppoConstant.ryhPubKey);
        ChannelStandardReqDto reqDto=new ChannelStandardReqDto();
        reqDto.setKey(key);
        reqDto.setVersion("1.0");
        reqDto.setMethod("union.login");
        reqDto.setAppId("RYH");
        reqDto.setFlowNo("RYH82464133366030162540873340");
        reqDto.setTimestamp(System.currentTimeMillis()+"");
        Map<String,Object> params=new HashMap<>();
        params.put("mobileNo","18870936109");
        String paramsStr= AesNewUtil.encrypt(JSON.toJSONString(params),aesKey);
        reqDto.setParams(paramsStr);
        Map<String,String> paramSend=JSON.parseObject(JSON.toJSONString(reqDto),Map.class);
        String sign=OppoSignatureUtils.sign(OppoConstant.ryhPriKeyTest,toSortedString(paramSend));
        reqDto.setSign(sign);
        System.out.println(JSON.toJSONString(reqDto));
    }


    /**
     * 将需要加签的数据拼接为'='和'&'拼接的格式
     * @param map
     * @return
     */
    private static String toSortedString(Map<String, String> map) {
        TreeSet<String> sortedKeys = new TreeSet<>(map.keySet());
        StringBuilder sb = new StringBuilder();
        for (String key : sortedKeys) {
            String value = map.get(key);
            sb.append(key).append("=").append(value).append("&");
        }
// 删除最后一个多余的 "&" 符号
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        String result = sb.toString();
        System.out.println("result is "+result);
        return result;
    }
}
