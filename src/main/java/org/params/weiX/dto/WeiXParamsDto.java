package org.params.weiX.dto;

import lombok.Data;

@Data
public class WeiXParamsDto {
    private String ryhPubKey;
    private String ryhPriKey;
    private String weiXPubKey;
    private String md5Key;
    private String channelCode;
    private String channelSignature;
    private String callBackUrl;
}
