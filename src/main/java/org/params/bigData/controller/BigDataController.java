package org.params.bigData.controller;

import com.alibaba.fastjson.JSONObject;
import org.params.bigData.utils.BusinessPushUtil;

public class BigDataController {


    public static void main(String[] args) {
//        JSONObject object=new JSONObject();
//        object.put("phone","5b7881b6158ceeb7434b937b0c5507ac");
//        System.out.println(BusinessPushUtil.encrypt(object.toJSONString()));
//        System.out.println(BusinessPushUtil.decrypt("ai9oPFIEHbPtSOQsKNG9AFwfWl8W0EO/jcItg8e06v4="));
//        System.out.println(BusinessPushUtil.decrypt("x/OGjFVIRpiLS3KGVqQOd9/Z/xQ7wg6QC1JwYhC1cu4mb1KVEFuBoVYGPDggV3HK8Z+wBLB0Gk27UKFAOa5459TDfiIdtm5useVBEKGGbbcENBDBi8ZuAy5+tO7k1jHfRL8lqCn82x8fxhE2I/YsP6710oN8fXqYvU6dqCzM7+jz37eQ8VIzHhnD4up1dJGlBEqO886Tys0kKcjVNiJjNWEZ0G5VsK6le3Vz9K2chLNjCpn6bwwAnlH5NINoL7pvfjBLWCExLz6Q3ejxu6yp2jl5/c6TEmr83A1WXeDrEZ7Y0krTKe2UzLey31r/lf9svLt5jtjg68LPS3HJpxidVtRpskqAfY+h/LG14ceiM+lc5enDp5IEZEZ5v/oSmopfMdIezvokkRCOT1t2I526QPL8eCX7RlHMD33Pgl5qVzQsrCBjZO4ZAurTBV0bviPweqJsfP9bWHKEC4mesKYryGTOaB23hSFr+Bi2JLp+0qLMU/vW49Tl7HlAMOR6TyVOdM/IoXOhQKLF5mQeV+X6BRdshAn0SFgM8Dpj/WuF6JfePPKrS4GL1U7mte57AztVI4RVLtniMtoi/pmILr3GX5Fok92a5TPg2VkiQysQaOddA6jHkv/yDR4fU7XShR/hV3VVggsttdjRhy0+mhu0YYOgAvbDc9HAZ2kb5Tzf7z+cSyumcnmPC2+hMpE0qtjzy6+jrwWu2rJzzcH8DqTEaNUrcuYIaUcJIKqrGGyz1spI6yPdN62QKT9S1Y0Bf73x"));
//
        String htmlUrl="https://api.rongyouhua.com/ystg/html/rxy10.html?channelSign=r01kklBaqA0khjaxChxabuFrzD8uXYvlQRu9Stnv1j";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("url", htmlUrl);
        // 短链类型(1=普通短链,2=微信短链)
        jsonObject.put("shortUrlType", 1);
        jsonObject.put("shortUrlName", "ss");
        jsonObject.put("expireTime", 30);
//        String env = sysConfigService.selectConfigByKey("sys_environment");
        String url = false ? "https://cqrxb.com" : "https://c.hzbxhd.com";
        String paramBodyReq = BusinessPushUtil.encrypt(jsonObject.toJSONString());
        System.out.println(paramBodyReq);
//        JSONObject req = new JSONObject();
//        req.put("data",paramBodyReq);
//        String response = HttpUtils.httpPostRequest(url + "/system/url/generateShortUrl/3", req.toJSONString());
//        System.out.println(response);
    }

}
