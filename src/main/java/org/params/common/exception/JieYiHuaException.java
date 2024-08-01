package org.params.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.params.common.HttpStatus;

/**
 * @author LiYu
 * @version 3.0
 * @description: system模块全局异常
 * @date 2021/7/22 16:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class JieYiHuaException extends RuntimeException {
    private static final long serialVersionUID = 277335181837623821L;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 异常信息
     */
    private String msg;

    public JieYiHuaException(String msg) {
        this.msg = msg;
        this.code = HttpStatus.ERROR;
    }

    public JieYiHuaException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
