package org.params.xyqb;

import com.alibaba.fastjson.JSONObject;
import org.params.maxiaomi.MxmPushReq;
import org.params.xiaoan.utils.SecureUtils;
import org.params.yql.utils.CommonUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/xyqb")
public class XyqbContorller {

    String msg= """
            {
              "accountNo": "Q8MblvEaOopNX0vkswIPohRMTfCr4juuqWWmjhfTNw",
              "mobile": "17610000046",
              "username": "测试",
              "age": 30,
              "sex": 1,
              "city": "成都市",
              "timestamp": 1734415672,
              "mobilePlatform": "android",
              "ip": "110.185.172.183",
              "fieldInfo": {
                "loadAmount": 3,
                "zhima": 2,
                "house": 2,
                "car": 2,
                "pubFund": 2,
                "guarantor": 2,
                "creditRecord": 1,
                "creditCard": 1,
                "socialSecurity": 2,
                "weilidai": 1,
                "term": 2,
                "baitiao": 1,
                "work": 1,
                "purpose": 2,
                "salarySettleType": 1,
                "education": 1,
                "monthIncome": 5500
              }
            }
            """;

    @PostMapping(value = "/params")
    public String params(@RequestBody String params) throws Exception {
        String key="af4bd50a21fe0851a24c7bf3";
        String data= SecureUtils.AesUtil.encrypt(params,key);
        String resultData= """
                {"data":"%s"}
                """;
        resultData=String.format(resultData,data);
        return resultData;
    }
}
