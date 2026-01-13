package org.params.wk;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/wk")
public class WkController {


    //国美制造撞库参数
    @PostMapping(value = "/hitParams")
    public String params(@RequestBody String params) throws Exception {
        WkReq wkReq=new WkReq();
        String aesKey= WkUtils.generateAESKey();
        wkReq.setData( WkUtils.encryptData(params,aesKey));
       // 对aesKey进行公钥加密
        wkReq.setKey(WkUtils.encryptKey(WkEnums.WKCONFIG_TEST.getPubKey(),aesKey));
        wkReq.setChannelCode("cHDBnptSk7WM644w7RrFzkuKt3JgV1M1yPddrTw9st");
        return JSONObject.toJSONString(wkReq);
    }
}
