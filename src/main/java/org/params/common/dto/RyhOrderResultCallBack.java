package org.params.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "订单状态回调", description = "订单状态回调")
public class RyhOrderResultCallBack {

    @ApiModelProperty("进件单号")
    @NotBlank(message = "进件单号 不能为空")
    private String orderNo;

    @ApiModelProperty("审核状态")
    @NotNull(message = "审核状态 不能为空")
    private Integer status;

    @ApiModelProperty("审批金额")
    private Long actualAmount;

    @ApiModelProperty("更新时间")
    private String updateTime;
}
