package org.params.maxiaomi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.params.guomei.RequestParams;
import org.params.guomei.constants.GmCommonConsts;
import org.params.xiaoan.utils.SecureUtils;
import org.params.yql.utils.CommonUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value = "/maxiaomi")
public class MaXiaoMiController {

    //国美制造撞库参数
    @GetMapping(value = "/params")
    public String params(@RequestBody String params) throws Exception {
        String key="097626a2aceb6f64";
        String data= SecureUtils.AesUtil.encrypt(params,key);
        MxmPushReq mxmPushReq=new MxmPushReq();
        mxmPushReq.setTimestamp(System.currentTimeMillis()+"");
        mxmPushReq.setChannel("ro5NtnbnrkB9Sw9ZwGhPOuRQ4bDFkOPXsVHUlYBjWq");
        mxmPushReq.setData(data);
//        String sign= """
//                channel=%s&data=%s&timestamp=%s%s
//                """;
//        String fomaStr=String.format(sign,mxmPushReq.getChannel(),mxmPushReq.getData(),mxmPushReq.getTimestamp(),key);
        Map<String,Object> signData=JSONObject.parseObject(JSONObject.toJSONString(mxmPushReq),Map.class);
        signData.remove("sign");
        String formatSign=formatUrlMap(signData);
        formatSign=CommonUtils.safeMd5(formatSign+key);
        mxmPushReq.setSign(formatSign);
        return JSONObject.toJSONString(mxmPushReq);
    }

    private  String formatUrlMap(Map<String, Object> paraMap) {
        String buff;
        Map<String, Object> tmpMap = paraMap;
        List<Map.Entry<String, Object>> infoIds = new ArrayList<>(tmpMap.entrySet());
        // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        Collections.sort(infoIds, Comparator.comparing(o -> (o.getKey())));
        // 构造URL 键值对的格式
        StringBuilder buf = new StringBuilder();
        for (Map.Entry<String, Object> item : infoIds) {
            if (Objects.nonNull(item.getKey())) {
                Object key = item.getKey();
                Object val = item.getValue();
                buf.append(key + "=" + val);
                buf.append("&");
            }
        }
        buff = buf.toString();
        if (!buff.isEmpty()) {
            buff = buff.substring(0, buff.length() - 1);
        }
        return buff;
    }
}
