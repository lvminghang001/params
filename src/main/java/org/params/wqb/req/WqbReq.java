package org.params.wqb.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class WqbReq implements Serializable {


    /**
     * 随机密钥密文key
     */
    private String key;

    /**
     * 签名 - 是
     * 加签顺序（根据key排序）：对输入参数按字典升序排序 此字段为RAS非对称加密
     */
    private String sign;

    /**
     * 时间戳 - 是
     */
    private Long timestamp;

    /**
     * 业务数据 - 否
     * json的字符串形式的加密后的密文（先加密，后加签名） 此字段为AES对称加密
     */
    private String data;
}
