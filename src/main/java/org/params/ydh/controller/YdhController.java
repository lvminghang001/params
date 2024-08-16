package org.params.ydh.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.params.common.AjaxResult;
import org.params.common.dto.RyhAuditResultCallback;
import org.params.common.dto.RyhOrderResultCallBack;
import org.params.common.dto.RyhRequestVo;
import org.params.common.utils.DateUtils;
import org.params.common.utils.RyhRsaUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(value = "/ydh")
public class YdhController {

    String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgIieQmPj3Xo9IyMN2vyXtk4Wg5qTMr8OKeTpC+7cjbDr3CfwMCckcllg3oOh6C+w5hsFNL7hEwyxkwoUXzJ9r0haUpxri+LouFA1JEdyuMOqa3hYLNuQbGIDrG9j/xc3Ka+nykDFga99NdeC28cWvY28+lhu3l9pFKcmDBoCgDwIDAQAB";
    String privateKey="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKAiJ5CY+Pdej0jIw3a/Je2ThaDmpMyvw4p5OkL7tyNsOvcJ/AwJyRyWWDeg6HoL7DmGwU0vuETDLGTChRfMn2vSFpSnGuL4ui4UDUkR3K4w6preFgs25BsYgOsb2P/Fzcpr6fKQMWBr30114Lbxxa9jbz6WG7eX2kUpyYMGgKAPAgMBAAECgYBOgG/k8xkaK8ESbVllXU+6qBdaSbeAoKm7uLJXJA8jzmsZsEtfm2x8FzgoDTnqmu2zRdx9emdbTlL1Emcsw6Ni8ZK2Y8N/qkzWLh1RX3oxYYdjUihcGm35X7jUrq/y/K1WVdjy6vqA3B7tm0ofDJj5xioC5RXdbwOANHODTgmAAQJBAM0nvcL58tRvXZyUX/qoQjQ8701dQq+0SnfqBxiWnAaKCP90T+s479gIMuy8o94HJn1r2dNo1b1M38MRoLEEBI8CQQDH0faVpLxFVwyupcqEwQlYBNWwPwtPhVSZDMirgmBU73Xsl7uZ5j3pQqCziJTvmcRQ0drzdSgweSGkAun4kmyBAkBCZSw6393A9tHbDQILA015zoa8CQS+DKFVMb5eLNAOJbpHwoLi46hryCuDBoIaJ0JDsRXsH6+c9jYs0ZWp9FztAkAkTdZyO7rlEjrApGiWFAhhkIdOfTXN3diP8g2nc4mTBOq08KBqBrXq4msWE1OT8KkFUgtovjluDSP6i3j864WBAkADCApWPtiRzb6SBnaHvvwrXDJoFCUWGFiC6fq3CVdB8GVDWPuMdOZIewRiunnp2ClHzHfJhUVem2L+vzYJ39HQ";


    //审核结果回调参数Params
    @PostMapping(value = "/auditCallBack")
    public AjaxResult auditCallBack(@Valid @RequestBody RyhAuditResultCallback req) throws Exception {
        RyhRequestVo requestVo=new RyhRequestVo();
        requestVo.setMarketId(52L);
        requestVo.setData(RyhRsaUtil.encryptByPublicKey(JSON.toJSONString(req),publicKey));
        requestVo.setSign(RyhRsaUtil.sign(requestVo.getData(),privateKey));
        requestVo.setProductNo("YiDeHua");
        System.out.println(JSONObject.toJSONString(requestVo));
        return AjaxResult.success(requestVo);
    }

    //订单状态回调参数Params
    @PostMapping(value = "/orderCallBack")
    public AjaxResult orderCallBack(@Valid @RequestBody RyhOrderResultCallBack req) throws Exception {
        req.setUpdateTime(DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,new Date()));
        RyhRequestVo requestVo=new RyhRequestVo();
        requestVo.setMarketId(52L);
        requestVo.setData(RyhRsaUtil.encryptByPublicKey(JSON.toJSONString(req),publicKey));
        requestVo.setSign(RyhRsaUtil.sign(requestVo.getData(),privateKey));
        requestVo.setProductNo("YiDeHua");
        return AjaxResult.success(requestVo);
    }

}
