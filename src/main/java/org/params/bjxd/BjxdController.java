package org.params.bjxd;

import cn.hutool.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping(value = "/bjxd")
@RestController
public class BjxdController {
    @PostMapping(value = "/callBack")
    public String callBack(@RequestBody  String json,String prod) throws IOException{
        String timestamp = String.valueOf(System.currentTimeMillis());
        String body = BaJieXiaoDaiUtil.encrypt(json,"ByqenvI0IVAKluGl");
        String nonce = "wwb";
        String sha1 = BaJieXiaoDaiUtil.getSHA1(BaJieXiaoDaiConstants.TOKEN_TEST, timestamp, nonce, body);
        StringBuilder builder = new StringBuilder(String.format("https://%s.hzbxhd.com/prod-api/market/callback/bjxd/feishuCallBack?timestamp=%s&nonce=%s&msg_signature=%s"
        ,prod,timestamp,nonce,sha1));
        String result = HttpRequest.post(builder.toString()).body(body).header("Content-Type", "application/json").timeout(10000).execute().body();
        String enData =  BaJieXiaoDaiUtil.decrypt(result,"ByqenvI0IVAKluGl");
        return enData;
    }

}
