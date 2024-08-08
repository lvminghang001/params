package org.params.weiX.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EncryptDTO {

    @JsonProperty("biz_data")
    private String bizData;

    @JsonProperty("channel_code")
    private String channelCode;

    @JsonProperty("secret_key")
    private String secretKey;

    private String timestamp;

    private String sign;
}
