package org.params.shanqian.req;

import lombok.Data;

@Data
public class ShanQianDataReq {

    //渠道code
    private String channel;
    //将json格式的body数据加密后生成的字符串
    private String content;

}
