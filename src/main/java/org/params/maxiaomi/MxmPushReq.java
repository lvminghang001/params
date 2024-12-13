package org.params.maxiaomi;

import lombok.Data;

@Data
public class MxmPushReq {
    private String data;
    private String sign;
    private String timestamp;
    private String channel;
}
