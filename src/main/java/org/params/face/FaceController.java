package org.params.face;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.params.common.AjaxResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(value = "/face")
public class FaceController {

    @ApiOperation("小程序 - getFaceToken")
    @PostMapping("/getFaceToken")
    public AjaxResult getFaceToken (@RequestBody JSONObject data) throws IOException {
        String key="OBeGgTQFo70MRtBioayX3_EZ9Ya9Rdgh";
        String secret="pZfGHGaBS9s54HtdKjC8lkUFXqAYpZPq";
        String baseUrl=" https://api.megvii.com/faceid/lite";
        String url="";
        data.put("api_key",key);
        data.put("api_secret",secret);
        String getType=data.getString("rquestType");
        if("token".equals(getType)){
            url=baseUrl+"/get_token";
        } else if ("result".equals(getType)) {
            url=baseUrl+"/get_result";
        }else{
            return AjaxResult.error("请求类型有误！");
        }
        HttpResponse response=null;
        try {
            data.remove("rquestType");
            response =HttpUtil.createPost(url).form(data).execute();
            System.out.println(response.body());
            //post = HttpUtil.createPost(url, data.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
  //      String result=EntityUtils.toString(response.getEntity(), "utf-8");
        return AjaxResult.success("success",response.body());
    }


    @ApiOperation("小程序 - getFaceToken")
    @PostMapping("/getFaceTokenMap")
    public AjaxResult getFaceTokenMap (@RequestBody Map data) throws IOException {
        String key="OBeGgTQFo70MRtBioayX3_EZ9Ya9Rdgh";
        String secret="pZfGHGaBS9s54HtdKjC8lkUFXqAYpZPq";
        String baseUrl=" https://api.megvii.com/faceid/lite";
        String url="";
        data.put("api_key",key);
        data.put("api_secret",secret);
        String getType=data.get("rquestType").toString();
        if("token".equals(getType)){
            url=baseUrl+"/get_token";
        } else if ("result".equals(getType)) {
            url=baseUrl+"/get_result";
        }else{
            return AjaxResult.error("请求类型有误！");
        }
        HttpResponse response=null;
        try {
            data.remove("rquestType");
            response =HttpUtil.createPost(url).form(data).execute();
            System.out.println(response);
            //post = HttpUtil.createPost(url, data.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success(response.body());
    }
}
