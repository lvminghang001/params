package org.params.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 统一请求封装类   父类
 * @Author: wangxin
 * @Date: 2022/4/1
 */
@Data
@Accessors(chain = true)
public class Req implements Serializable {
    /**
     * @Description: 资方模块配置 唯一ID
     * @Author: wangxin
     * @Date: 2022/4/1
     */
    public Long marketId;
    /**
     * @Description: 业务枚举
     * @Author: wangxin
     * @Date: 2022/4/1
     */
    public String businessType;
}
