package org.params.yql;

import com.alibaba.fastjson2.JSON;
import org.params.common.AjaxResult;
import org.params.yql.dto.YqlHitParams;
import org.params.yql.dto.YqlPushParams;
import org.params.yql.dto.YqlRequest;
import org.params.yql.enums.YqlEnums;
import org.params.yql.utils.AesUtils;
import org.params.yql.utils.CommonUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 *   有钱来撞库进件参数制造
 */
@RestController
@RequestMapping(value = "/yql")
public class YqlController {
    String aesKey="0FAo6Lsj8xz4hSfYhE3/Vg==";

   // String aesKey="LEaAJ60+VcZ5I/ZVObamOQ==";  //pro
 //   String aesKey="0000000000000000";

    //有钱来参数制造
    @PostMapping(value = "/hitParams")
    public AjaxResult hitParams(@RequestBody YqlHitParams yqlHitParams) throws Exception {
        yqlHitParams.setMobileMD5(CommonUtils.safeMd5(yqlHitParams.getMobileMD5()));
        YqlRequest yqlRequest= YqlRequest.getInstance().clone()
                        .setMethod(YqlEnums.YQL_CHECK.getMethod()).setTimestamp(new Date().getTime())
                .setParams(AesUtils.encrypt(JSON.toJSONString(yqlHitParams), aesKey));
        createSign(yqlRequest);
        return AjaxResult.success(yqlRequest);
    }

    @PostMapping(value = "/pushParams")
    public AjaxResult pushParams(@RequestBody YqlPushParams yqlPushParams) throws Exception {
        YqlRequest yqlRequest= YqlRequest.getInstance().clone()
                .setMethod(YqlEnums.YQL_APPLY.getMethod()).setTimestamp(new Date().getTime())
                .setParams(AesUtils.encrypt(JSON.toJSONString(yqlPushParams),aesKey));
        createSign(yqlRequest);
        return AjaxResult.success(yqlRequest);
    }


    // Md5(method+timestamp+params+aesKey)
    void createSign(YqlRequest yqlRequest){
        String formatSign=String.format("%s%s%s%s",yqlRequest.getMethod(),yqlRequest.getTimestamp(),yqlRequest.getParams(),aesKey);
        String signMd5= CommonUtils.safeMd5(formatSign);
        yqlRequest.setSign(signMd5);
    }
}
