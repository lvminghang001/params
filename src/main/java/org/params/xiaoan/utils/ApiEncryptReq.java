package org.params.xiaoan.utils;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 全流程 - 信贷api - 加密参数
 * @author Administrator
 */
@Data
public class ApiEncryptReq implements Serializable {

    private static final long serialVersionUID = 6299251944414813822L;

    @NotBlank(message = "iv is null")
    private String iv;

    @NotBlank(message = "data is null")
    private String data;
    /**
     * 是否异步
     */
    private Boolean asynchronous = false;
}
