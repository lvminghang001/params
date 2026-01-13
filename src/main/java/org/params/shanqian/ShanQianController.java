package org.params.shanqian;

import com.alibaba.fastjson.JSONObject;
import org.params.shanqian.req.ShanQianCheckDetailsReq;
import org.params.shanqian.req.ShanQianDataReq;
import org.params.shanqian.req.ShanQianPushDetailsReq;
import org.params.yql.utils.CommonUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/shanqian")
public class ShanQianController {
    private final static String key="Cssulbz0B26tz2fN";
    private final static String iv="iJ7i6r1BNgYzwHPU";

    private final static String channelCode="14ilNokV9b64EtvCQPDsxZZv7poxGMOMzRsDPYv4uV";


    @PostMapping(value = "/hitParams")
    public String hitParams(@RequestBody String params) {
        ShanQianCheckDetailsReq req=JSONObject.parseObject(params,ShanQianCheckDetailsReq.class);
        req.setMobile_md5(CommonUtils.safeMd5(req.getMobile_md5()));
        req.setName_md5(CommonUtils.safeMd5(req.getName_md5()));
//        req.setMobile_md5(CommonUtils.safeMd5("18725459911"));
//        req.setName_md5(CommonUtils.safeMd5("张三"));
//        req.setGender(1);
//        req.setAge(25);
//        req.setCity("杭州");
//        req.setQuota(2);
//        req.setJob(2);
//        req.setHouse(2);
//        req.setCar(2);
//        req.setZm(2);
//        req.setSocial(2);
//        req.setFunds(2);
//        req.setInsurance(2);
//        req.setOverdue(1);
//        req.setClient_ip("115.206.206.178");
//        req.setRedirect_url("");
//        req.setProduct_id(String.valueOf(System.currentTimeMillis()));
        System.out.println(CreditApiAesUtils.encrypt(JSONObject.toJSONString(req),key,iv));
        ShanQianDataReq shanQianDataReq=new ShanQianDataReq();
        shanQianDataReq.setContent(CreditApiAesUtils.encrypt(JSONObject.toJSONString(req),key,iv));
        shanQianDataReq.setChannel(channelCode);
        return JSONObject.toJSONString(shanQianDataReq);
    }

    @PostMapping(value = "/pushParams")
    public String pushParams(@RequestBody String params) {
        ShanQianPushDetailsReq req=JSONObject.parseObject(params,ShanQianPushDetailsReq.class);
//        req.s(CommonUtils.safeMd5("18725459911"));
//        req.setName_md5(CommonUtils.safeMd5("张三"));
//        req.setGender(1);
//        req.setAge(25);
//        req.setCity("杭州");
//        req.setQuota(2);
//        req.setJob(2);
//        req.setHouse(2);
//        req.setCar(2);
//        req.setZm(2);
//        req.setSocial(2);
//        req.setFunds(2);
//        req.setInsurance(2);
//        req.setOverdue(1);
//        req.setClient_ip("115.206.206.178");
//        req.setRedirect_url("");
//        req.setProduct_id(String.valueOf(System.currentTimeMillis()));
        System.out.println(CreditApiAesUtils.encrypt(JSONObject.toJSONString(req),key,iv));
        ShanQianDataReq shanQianDataReq=new ShanQianDataReq();
        shanQianDataReq.setContent(CreditApiAesUtils.encrypt(JSONObject.toJSONString(req),key,iv));
        shanQianDataReq.setChannel(channelCode);
        return JSONObject.toJSONString(shanQianDataReq);
    }
}

