package org.params.longxianghua;

import com.alibaba.fastjson.JSONObject;
import org.params.common.utils.AES256Util;
import org.params.longxianghua.dto.LxhHitParams;
import org.params.longxianghua.dto.LxhPushParams;
import org.params.shanqian.CreditApiAesUtils;
import org.params.shanqian.req.ShanQianCheckDetailsReq;
import org.params.shanqian.req.ShanQianDataReq;
import org.params.shanqian.req.ShanQianPushDetailsReq;
import org.params.xiaoan.utils.SecureUtils;
import org.params.yql.utils.CommonUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/lxh")
public class LongXiangHuaController {

    private final static String key="]+atg}`Ff*bcH[99M#-G%rI,s-n;nMR=";
    private final static String iv="iJ7i6r1BNgYzwHPU";

    private final static String channelCode="14ilNokV9b64EtvCQPDsxZZv7poxGMOMzRsDPYv4uV";


    @PostMapping(value = "/hitParams")
    public String hitParams(@RequestBody String params) {
        LxhHitParams req= JSONObject.parseObject(params,LxhHitParams.class);
        req.setPhoneMd5(CommonUtils.safeMd5(req.getPhoneMd5()));
        req.setName(CommonUtils.safeMd5(req.getName()));
        req.setChannelCode(channelCode);
//        String data=org.params.bigData.utils.SecureUtils.AesUtil.decrypt(JSONObject.toJSONString(req),key);
        return AES256Util.encrypt(JSONObject.toJSONString(req));
//        return JSONObject.toJSONString(req);
    }

    @PostMapping(value = "/pushParams")
    public String pushParams(@RequestBody String params) {
        LxhPushParams req= JSONObject.parseObject(params,LxhPushParams.class);
        req.setChannelCode(channelCode);
        req.setPhone(AES256Util.encrypt(req.getPhone()));
        req.setName(AES256Util.encrypt(req.getName()));
        return JSONObject.toJSONString(req);
    }

    public static void main(String[] args) {
        System.out.println(AES256Util.encrypt("aaa"));
    }
}
