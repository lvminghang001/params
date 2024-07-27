package org.params.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 小安 - 回调通用请求
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RyhRequestVo extends Req implements Serializable {
    //渠道编码
    private String channelNo;
    //产品编码
    @NotBlank(message = "产品编码不能为空")
    private String productNo;
    //签名
    @NotBlank(message = "签名不能为空")
    private String sign;
    //时间戳
    private String timestamp;
    //业务数据
    @NotBlank(message = "业务数据不能为空")
    private String data;


}
