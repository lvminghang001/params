package org.params.guomei.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GuoMeiPushDto {
    //申请编码
    private String creditApplyNo;
    //合作方编码
    private String capitalCode;
    //申请额度
    private BigDecimal applyLmt;
    //异步回调地址
    private String callBackUrl;
    //进件信息
    private ApplicationDto applicationDto;
    //时间戳
    private Long applyTime;
}
