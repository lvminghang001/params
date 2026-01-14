package org.params.bjxd;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

/**
 *    回调模拟线上减免审批通过
 */
@RequestMapping(value = "/bjxd")
@RestController
public class BjxdController {

    // 请求参数
    // 192.168.100.218:10086/bjxd/callBack?prod=jxhln
    public String aesKey = "ByqenvI0IVAKluGl";

    public static String reqParam = """
            {
                "data": {
                    "instanceCode": "112278",
                    "status": "APPROVED"
                },
                "notifyType": "1"
            }
            """;

    @PostMapping(value = "/callBack")
    public String callBack(@RequestBody  String json,String prod) throws IOException{
        String timestamp = String.valueOf(System.currentTimeMillis());
        String body = BaJieXiaoDaiUtil.encrypt(json,aesKey);
        String nonce = "wwb";
        String sha1 = BaJieXiaoDaiUtil.getSHA1(BaJieXiaoDaiConstants.TOKEN_TEST, timestamp, nonce, body);
        StringBuilder builder = new StringBuilder(String.format("https://%s.hzbxhd.com/prod-api/market/callback/bjxd/feishuCallBack?timestamp=%s&nonce=%s&msg_signature=%s"
        ,prod,timestamp,nonce,sha1));
        String result = HttpRequest.post(builder.toString()).body(body).header("Content-Type", "application/json").timeout(10000).execute().body();
        if(ObjectUtil.isNotEmpty(result) && result.contains("令牌")){
            System.out.println("鉴权失败，应该是未开放白名单");
            return "鉴权失败，应该是未开放白名单";
        }
        return BaJieXiaoDaiUtil.decrypt(result,aesKey);
    }

}
