package org.params.xiaoan.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.params.common.AjaxResult;
import org.params.xiaoan.utils.*;
import org.params.yql.dto.YqlHitParams;
import org.params.yql.dto.YqlRequest;
import org.params.yql.enums.YqlEnums;
import org.params.yql.utils.AesUtils;
import org.params.yql.utils.CommonUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *   小安参数制造
 */
@RestController
@RequestMapping(value = "/xiaoan")
public class XiaoAnController {

    @PostMapping(value = "/params")
    public ApiEncryptReq params(@RequestBody JSONObject jsonObject) throws Exception {
        String ddfqKey = SecureUtils.AesUtil.generateDdfqKey(ChannelType.RONG_YOU_HUA.name());
        String iv=SecureUtils.AesUtil.getIv();
        String decryptedData = SecureUtils.AesUtil.encryptCbc(jsonObject.toJSONString(), ddfqKey, iv);
        ApiEncryptReq apiEncryptReq=new ApiEncryptReq();
        apiEncryptReq.setIv(iv);
        apiEncryptReq.setData(decryptedData);
        return apiEncryptReq;
    }

    @PostMapping("/fullCreateParams")
    public AjaxResult pushVT(@Valid @RequestBody ApiFullCheckReq apiFullCheckReq) {
        return createParams(apiFullCheckReq);
    }

    public AjaxResult createParams(ApiFullCheckReq apiFullCheckReq) {
        com.alibaba.fastjson.JSONObject object=new com.alibaba.fastjson.JSONObject();
        object.put("phoneMd5",CommonUtils.safeMd5(apiFullCheckReq.getPhoneMd5()));
        object.put("nameMd5",CommonUtils.safeMd5(apiFullCheckReq.getNameMd5()));
        object.put("sex",apiFullCheckReq.getSex());
        object.put("age",apiFullCheckReq.getAge());
        object.put("city",apiFullCheckReq.getCity());
        object.put("socialSecurity",apiFullCheckReq.getSocialSecurity());
        object.put("accumulationFund",apiFullCheckReq.getAccumulationFund());
        object.put("carProduction",apiFullCheckReq.getCarProduction());
        object.put("estate",apiFullCheckReq.getEstate());
        object.put("unitSocialSecurity",apiFullCheckReq.getUnitSocialSecurity());


        object.put("sesame",apiFullCheckReq.getSesame());
        object.put("professionalIdentity",apiFullCheckReq.getProfessionalIdentity());
        object.put("customerCreditCard",apiFullCheckReq.getCustomerCreditCard());
        object.put("highestEducation",apiFullCheckReq.getHighestEducation());
        object.put("monthlyIncome",apiFullCheckReq.getMonthlyIncome());
        object.put("customerFormOfPayroll",apiFullCheckReq.getCustomerFormOfPayroll());
        object.put("lengthOfService",apiFullCheckReq.getLengthOfService());
        object.put("loanPurpose",apiFullCheckReq.getLoanPurpose());
        object.put("ip",apiFullCheckReq.getIp());
        object.put("channelSignature",apiFullCheckReq.getChannelSignature());

        object.put("deviceType",apiFullCheckReq.getDeviceType());
        object.put("huaBeiQuota",apiFullCheckReq.getHuaBeiQuota());
        object.put("baiTiaoQuota",apiFullCheckReq.getBaiTiaoQuota());
//        object.put("env",apiFullCheckReq.getUnitSocialSecurity());
        String ddfqKey = SecureUtils.AesUtil.generateDdfqKey(ChannelType.RONG_YOU_HUA.name());
        String iv=SecureUtils.AesUtil.getIv();
        System.out.println("iv is "+iv);
        String hitData=SecureUtils.AesUtil.encryptCbc(object.toJSONString(),ddfqKey,iv);
        System.out.println("撞库参数 "+hitData);
//        String decryptedData = SecureUtils.AesUtil.decryptCbc(datas, ddfqKey, iv);
//        System.out.println("解密后"+decryptedData);
        object.remove("phoneMd5");
        object.remove("nameMd5");
        object.put("idCard",apiFullCheckReq.getIdCardMd5());
        object.put("name",apiFullCheckReq.getNameMd5());
        object.put("phone",apiFullCheckReq.getPhoneMd5());
        String pushData=SecureUtils.AesUtil.encryptCbc(object.toJSONString(),ddfqKey,iv);
        Map<String,Object> returnData=new HashMap<>();
        com.alibaba.fastjson.JSONObject hit=new com.alibaba.fastjson.JSONObject();
        hit.put("iv",iv);
        hit.put("data",hitData);
        com.alibaba.fastjson.JSONObject push=new com.alibaba.fastjson.JSONObject();
        push.put("iv",iv);
        push.put("data",pushData);
//        returnData.put("撞库参数",hit.toJSONString());
//        returnData.put("进件参数",push.toJSONString());
        returnData.put("撞库参数",hit);
        returnData.put("进件参数",push);
        return AjaxResult.success(returnData);
    }


    public static void main(String[] args) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("phone","18901235896");
        jsonObject.put("channelSign","5331");
//        String ddfqKey = SecureUtils.AesUtil.generateDdfqKey(ChannelType.RONG_YOU_HUA.name());
//        String iv=SecureUtils.AesUtil.getIv();
//        String decryptedData = SecureUtils.AesUtil.encryptCbc(jsonObject.toJSONString(), ddfqKey, iv);
//        ApiEncryptReq apiEncryptReq=new ApiEncryptReq();
//        apiEncryptReq.setIv(iv);
//        apiEncryptReq.setData(decryptedData);
//        System.out.println(JSON.toJSONString(apiEncryptReq));
       // String sign=SecureUtils.AesUtil.encrypt(jsonObject.toJSONString(),"b4c4bf93637e4864");
        System.out.println(SignUtils.encrypt(jsonObject.toJSONString(), "2%8iTpSi"));
    }
}
