package org.params.wk;

import lombok.Data;

/**
 *    请求参数
 */
@Data
public class WkReq {
    private String requestNo;
    private String channelCode;
    private String data;
    private String key;
    private Long timeStamp;
    private String sign;
}
