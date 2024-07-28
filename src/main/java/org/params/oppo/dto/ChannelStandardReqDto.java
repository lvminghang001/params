package org.params.oppo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author XuYu
 * @program channel-demo->ChannelStandardReqDto
 * @description
 * @create 2021-11-02 16:20
 **/
@Data
public class ChannelStandardReqDto implements Serializable {

    private static final long serialVersionUID = -393548433836206011L;
    /**
     * 合作方ID
     */
    private String appId;

    /**
     * 请求流水号
     */
    private String flowNo;

    /**
     * 调用接口名
     */
    private String method;

    /**
     * 接口版本号
     */
    private String version;

    /**
     * 请求参数
     */

    private String params;

    /**
     * 请求密钥
     */
    private String key;

    /**
     * 签名
     */
    private String sign;

    /**
     * 请求时间戳
     */
    private String timestamp;

}
