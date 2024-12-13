package org.params.halo;

import lombok.SneakyThrows;
import org.params.common.AjaxResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/halo")
public class HaloController {

   static String hit= """
            {"custName":"李刚开","idNo":"73DEFC725D092DAC49582280FED5D115","mobileNo":"6731E0426FEE6BC6D5B88159D02EB3DA"}
            """;


    String push= """
            {"applyNo":"7286687501843431484","idNo":"310101199412180286","certIssueAuthority":"化州市公安局","mobileNo":"18953749268","customerName":"李刚开","liveProvinceName":"北京","liveCityName":"北京市","liveDistrictName":"东城区","liveProvinceCode":"110000","liveCityCode":"110100","liveDistrictCode":"110101","liveAddress":"自动化测试地址3467号","certValidateStart":"1452096000000","certValidateEnd":"2083248000000","certGender":"男","certBirthday":"787680000000","certEthnic":"汉","certAddress":"广东省化州市平定镇平山新屋村23号","images":"{\\"livingImageInfos\\":\\"https://avenger-bioauth.cn-hangzhou.oss.aliyuncs.com/570451011458187264_1587612531_570451060690927616?Expires=1734072410&OSSAccessKeyId=LTAI4FgsrTpDLz2svPGKDDBf&Signature=Msv0RpzmqV5pjFIx3aPwx9AiRlg%3D\\",\\"idCardFrontInfo\\":\\"https://hello-finance.cn-hangzhou.oss.aliyuncs.com/pro/user/id/1/4466244270345243105-1691895479287.png?Expires=1733878010&OSSAccessKeyId=LTAIsm7RWG696r8U&Signature=nJcdP%2F6rQ%2FaKymLImYYmWitMbXs%3D\\",\\"idCardBackInfo\\":\\"https://hello-finance.cn-hangzhou.oss.aliyuncs.com/pro/user/id/0/4466244270345243105-1691895493814.png?Expires=1733878010&OSSAccessKeyId=LTAIsm7RWG696r8U&Signature=MczodCoKbVgUBlvqY9isjsz7F10%3D\\"}","contacts":[{"mobile":"15689898989","name":"张三","relation":"7"},{"mobile":"15623235656","name":"李四","relation":"12"}],
                                                                                                                        "education":"0","salary":"6","marital":null,"industry":null,"profession":null,"companyName":null,"companyProvinceCode":null,"companyCityCode":null,"companyDistrictCode":null,"companyAddressDetails":null,"loanUsage":null,"similarPoint":"95",
                                                                                                                        "userDeviceInfo":"{\\"screenWidth\\":\\"390\\",\\"appVersion\\":\\"6.70.0\\",\\"memory\\":3822911488,\\"freeSize\\":39754342400,\\"os\\":\\"iOS\\",\\"bssid\\":\\"0:4e:35:8f:32:16\\",\\"screenHeight\\":\\"844\\",\\"idfa\\":\\"E78D42BF-82FE-4F5D-9427-D7CF1849715E\\",\\"hello_token\\":\\"7279070767522123700\\",\\"deviceName\\":\\"iPhone\\",\\"ssi
                                                                                                                        d\\":\\"HelloBike-Staff\\",\\"manufacturer\\":\\"Apple\\",\\"network\\":\\"WiFi\\",\\"h5uuid\\":\\"17253446437103595\\",\\"totalSize\\":63870980096,\\"osVersion\\":\\"17.5.1\\",\\"appList\\":\\"支付宝\\",\\"boottime\\":1729509871296,\\"root\\":false,\\"fingerprint-hash\\":\\"304b15406204064716ac76768b3f8396d9521778ca5590d6878c45030b160a7c\\",\\"batteryChargeStatus\\":0,\\"idfv\\":\\"32D2D663-DEC4-4A40-B07C-B565435AA9E7\\",\\"deviceModel\\":\\"iPhone13,2\\"}","faceSource":"TENCENT_OCR","facePoint":"99","applyModel":"MAJORITY"}
            """;

    //审核结果回调参数Params
    @PostMapping(value = "/params")
    public String hitParams(@Valid @RequestBody String json) throws Exception {
        System.out.println("明⽂A====:"+json);
        /******************以下为加密加签流程*************************/
        //获取随机密钥R
        String R =SecurityDemoUtil.getAESRandomKey();
        System.out.println("随机密钥明⽂R:"+R);
        //使⽤随机密钥R对明⽂A进⾏AES对称加密,得到密⽂M
        String M =SecurityDemoUtil.encryptAES(json, R, "utf-8");
        System.out.println("请求密⽂M:"+M);
        String K = SecurityDemoUtil.encryptRSA(R, HaLuoEnum.TEST.getPublicKey(), "utf-8");
        System.out.println("随机密钥密⽂K:"+K);
        String B = SecurityDemoUtil.genMD5(json);
        System.out.println("请求明⽂MD5加密B:"+B);
        //加签
        String S = SecurityDemoUtil.encryptRSA(B,HaLuoEnum.TEST.getPublicKey(), "utf-8");
        System.out.println("签名S:"+S);
        String result= """
                {
                  "appid": "yourAppId",
                  "data": "%s",
                  "key": "%s",
                  "reqid": "yourReqId",
                  "timestamp": 1638384000000,
                  "sign": "%s"
                }
                """;
        result=String.format(result, M, K, S);
        return result;
    }

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println("明⽂A====:"+hit);
        /******************以下为加密加签流程*************************/
        //获取随机密钥R
        String R =SecurityDemoUtil.getAESRandomKey();
        System.out.println("随机密钥明⽂R:"+R);
        //使⽤随机密钥R对明⽂A进⾏AES对称加密,得到密⽂M
        String M =SecurityDemoUtil.encryptAES(hit, R, "utf-8");
        System.out.println("请求密⽂M:"+M);
        String K = SecurityDemoUtil.encryptRSA(R, HaLuoEnum.TEST.getPublicKey(), "utf-8");
        System.out.println("随机密钥密⽂K:"+K);
        String B = SecurityDemoUtil.genMD5(hit);
        System.out.println("请求明⽂MD5加密B:"+B);
        //加签
        String S = SecurityDemoUtil.encryptRSA(B,HaLuoEnum.TEST.getPublicKey(), "utf-8");
        System.out.println("签名S:"+S);

//        System.out.println(SecurityDemoUtil.encryptRSA(hit, HaLuoEnum.TEST.getPublicKey(), HaLuoEnum.TEST.getInputCharset()));
        String result= """
                {
                  "appid": "yourAppId",
                  "data": "%s",
                  "key": "%s",
                  "reqid": "yourReqId",
                  "timestamp": 1638384000000,
                  "sign": "%s"
                }
                """;
        System.out.println(String.format(result, M, K, S));
    }
}
