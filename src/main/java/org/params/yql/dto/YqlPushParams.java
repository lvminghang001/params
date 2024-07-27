package org.params.yql.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

@Data
public class YqlPushParams extends YqlBaseParams{
    private String mobile;

    private String realName;

    private String identity;

    private List<JSONObject> protocolUrls;
}
