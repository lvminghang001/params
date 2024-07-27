package org.params.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "审核结果回调对象", description = "审核结果回调对象")
public class RyhAuditResultCallback {

    @ApiModelProperty("进件单号")
    @NotBlank(message = "进件单号 不能为空")
    private String orderNo;

    @ApiModelProperty("审核状态")
    @NotNull(message = "审核状态 不能为空")
    private Integer status;

    @ApiModelProperty("审核时间")
    @NotBlank(message = "审核时间 不能为空")
    private String auditTime;

    @ApiModelProperty("审批期限 若审核通过，此字 段必填")
    private Integer auditPeriodNum;

    @ApiModelProperty("审批期限单位")
    private Integer auditPeriodNumUnit;

    @ApiModelProperty("审批金额 单位：分。 若审核 通过，此字段必 填")
    private Long auditAmount;

    @ApiModelProperty("拒绝原因 审核失败时，传 原因")
    private String rejectReasons;



    @ApiModelProperty("审批金额最小值")
    private Long auditMinAmount;

    @ApiModelProperty("金额递增 - 如用户输入借款金额只能输入整百、整千等，未传输时默认100递增")
    private Long amountIncr;

    @ApiModelProperty("年化利率")
    private BigDecimal yearRate;




    @ApiModelProperty("自用 - 与三方无关")
    private String marketCode;
}
