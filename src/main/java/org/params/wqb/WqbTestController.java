package org.params.wqb;


import com.alibaba.fastjson.JSONObject;
import org.params.wqb.resp.WqbResp;
import org.params.yql.utils.CommonUtils;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;

@RestController
@RequestMapping(value = "/wqbTest")
public class WqbTestController {



    @GetMapping(value = "/hit")
    public String hit(@RequestParam("phone") String phone, @RequestParam("idCard") String idCard) {
        JSONObject hitData=new JSONObject();
        hitData.put("phoneMd5",CommonUtils.safeMd5(phone));
        hitData.put("idCardMd5",CommonUtils.safeMd5(idCard));
        WqbResp wqbResp=WqbCommonRSAUtil.encryptionAndSignAndBuild(JSONObject.toJSONString(hitData),WqbConfigEnum.TEST);
        return JSONObject.toJSONString(wqbResp);
    }

    @PostMapping(value = "/push")
    public String push(@RequestBody String json) {
        try {
            WqbPushParamReq wqbPushParamReq=JSONObject.parseObject(json,WqbPushParamReq.class);
            wqbPushParamReq.setApplyNo("YSH"+secure15DigitRandom());
            wqbPushParamReq.setPhoneMd5(CommonUtils.safeMd5(wqbPushParamReq.getPhone()));
            wqbPushParamReq.setIdCardMd5(CommonUtils.safeMd5(wqbPushParamReq.getIdCardMd5()));
            WqbResp wqbResp2=WqbCommonRSAUtil.encryptionAndSignAndBuild(JSONObject.toJSONString(wqbPushParamReq),WqbConfigEnum.TEST);
            return JSONObject.toJSONString(wqbResp2);
        }catch (Exception e){
            return "转换参数异常"+e.getMessage();
        }

    }


    public  String secure15DigitRandom() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        // 生成加密安全的随机数[4,5](@ref)
        sb.append(secureRandom.nextInt(9) + 1);
        for (int i = 0; i < 14; i++) {
            sb.append(secureRandom.nextInt(10));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String phone="18870253624";
        System.out.println(CommonUtils.safeMd5(phone));
        String idCard="330105199911240362";
        JSONObject hitData=new JSONObject();
        hitData.put("phoneMd5",CommonUtils.safeMd5(phone));
        hitData.put("idCardMd5",CommonUtils.safeMd5(idCard));
        WqbResp wqbResp=WqbCommonRSAUtil.encryptionAndSignAndBuild(JSONObject.toJSONString(hitData),WqbConfigEnum.TEST);
        System.out.println(JSONObject.toJSONString(wqbResp));

        String pushData= """
                {"aliveAuthImageUrl":"https://file.grjianr.com/xiaowei/ocr/20250118/81885104db7daf23c5248dc9a27c79aa7996ae18188510573643.png","aliveAuthTime":"2025-04-15 09:48:01","applyNo":"ysh2025052011124951172442923","companyName":"晚上忙什新坡么呢",
                "contactName":"民生","contactPhone":"15876738659","contactRelationship":"99","credentialsInvalidDate":"2030-04-12","credentialsIssueDate":"2020-04-12","deviceType":"2",
                "faceScore":"90","gender":"F","highestEducationalLevel":"02","idAddress":"河南省南阳市方城县踩萍街道晶震小区5号楼2704","idCardBackImageUrl":"https://file.grjianr.com/xiaowei/ocr/20241226/286a8776f-a546-48f3-9675-ae7c36b5fb4f20241226165238.jpg.jpg",
                "idCardFrontImageUrl":"https://file.grjianr.com/xiaowei/ocr/20250118/81885103fb758002a0a4c57a87b8b4bbd05db878188510505380.png","idCardMd5":"60e0d769e2fcb8a780bd8d650eea112b","idCityCode":"411300","idCityName":"南阳市","idProvinceCode":"410000","idProvinceName":"河南省",
                "idcard":"330105199911240343","income":"5000","ip":"39.171.240.225","issuingOrgan":"方城县公安局","kinName":"敏敏","kinPhone":"15846738679","kinShip":"01","latitude":"30.323946","liveAddress":"欧珀婆婆子来一口咯咯咯","liveAreaCode":"330105","liveAreaName":"拱墅区","liveCityCode":"330100","liveCityName":"杭州市","liveProvinceCode":"330000","liveProvinceName":"浙江省","longitude":"120.106018","marriageStatus":"00","name":"恩行","nation":"汉","occupation":"02","permanent":"0",
                "phone":"18870253698","phoneMd5":"92edbda685d968e8fe5c5dfc87e929c3","workAddress":"夫妻之间明鑫牛肉酱热破","workAreaCode":"330105","workAreaName":"拱墅区","workCityCode":"330100","workCityName":"杭州市","workProvinceCode":"330000","workProvinceName":"浙江省","workUnit":"3"}
                """;
        WqbPushParamReq wqbPushParamReq=JSONObject.parseObject(pushData,WqbPushParamReq.class);
        wqbPushParamReq.setApplyNo("YSH9999999999999999889919");
        wqbPushParamReq.setPhone(phone);
        wqbPushParamReq.setIdcard(idCard);
        wqbPushParamReq.setPhoneMd5(CommonUtils.safeMd5(phone));
        wqbPushParamReq.setIdCardMd5(CommonUtils.safeMd5(idCard));
        WqbResp wqbResp2=WqbCommonRSAUtil.encryptionAndSignAndBuild(JSONObject.toJSONString(wqbPushParamReq),WqbConfigEnum.TEST);
        System.out.println(JSONObject.toJSONString(wqbResp2));
    }
}
