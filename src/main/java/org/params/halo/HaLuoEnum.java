package org.params.halo;

import lombok.Getter;

/**
 * 哈啰环境配置枚举
 */
@Getter
public enum HaLuoEnum {
    TEST("https://fat-finance-light.hellobike.com/gateway/openflow",
            "",
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDM1MvJw/yU6OFfvSEuU0h9eg9Y9Zm8RnSg"+
            "zV7Hgj/qU+IM8CeLI/kRxVmyHL4fyUQO5qRyhL80yRyFUKKjUlLhwbzbtKg3XLsFv+lhuA0C5/NPYmr"+
            "S8e/5LTLS0zNWDdMpJZEx9Y5TIPDnqxrDRsV2lNZd82CHzAd7aMe5fBatwQIDAQAB",
            "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMzUy8nD/JTo4V+9IS5TSH16D1j1" +
                    "mbxGdKDNXseCP+pT4gzwJ4sj+RHFWbIcvh/JRA7mpHKEvzTJHIVQoqNSUuHBvNu0qDdcuwW/6W" +
                    "G4DQLn809iatLx7/ktMtLTM1YN0yklkTH1jlMg8OerGsNGxXaU1l3zYIfMB3tox7l8Fq3BAgMBAAECgY" +
                    "BxV/tgrcPR/r/fw39d7BX74RQnDNjCV1ZoONyOl+OYXkyDDk1DcGd9zu/gYIlQe4XenQA4on5Pzk2q8" +
                    "8DbkU1swwoFhkvWFAOqF6UEYujhymY86MrobNXqztqO4RcF3OH5IrDfRGXtEr+fGNPwyu+IyjhNXx" +
                    "W5BdvobjmgGVVS9QJBAPiE8J/JvQxUmmucyGmPNNhnFMvBJiXjIq80ce9Yn+kFjJmP0VkTP0Y5b2" +
                    "uTdqVBdEghZESVPFzw5LPbAqOP1QMCQQDS/zN8Jh5R/0xodZkhcfKanN9Fa5wDIVjiIAY18iQiDR9" +
                    "ekMqUzl14LiDGa+fMhlVYXjUIiyBR0k2zOReJBgzrAkA+9J5oPBjR+NStkigK5aZDc8mG3EUnr+Rncee" +
                    "y9EZ+J1O4ywADiqaqyX36SH7z2iL06tCVtyB1gujMzxxaBuO1AkBZlF+XZdeZmHooH0VUHbySR+fC4" +
                    "VzrN001M8NvQ85zZn7a9z4Kz1J/o5XmqAlRm/a//b8mUWr3UgILBUIoupjhAkAh8gPg8eqpgTW1AVz" +
                    "AcSwREDyg2H2fz+AkKa+rb1eSdbAvqC4CObJVjw5m/QC/wumbwYPgeFXmCMmC+3cWw2Dh",
            "utf-8","fIIsJqkUuIMmViL8q0CLMHFKTGY0dmhjeX4oFuSvyV"),
    PRO("https://finance-light.hellobike.com/gateway/openflow",
            "",
            "publicKey",
            "privateKey",
            "utf-8","Jd7gXLCFrAgvKjNB1fsDmKMRT07tCaBVYYxI0bLIyN");

    HaLuoEnum(String url, String appid, String publicKey, String privateKey, String inputCharset, String channelSignature) {
        this.url = url;
        this.appid=appid;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.inputCharset = inputCharset;
        this.channelSignature = channelSignature;
    }

    /**
     * 获取请求地址
     */
    private final String url;
    /**
     *为调用方分配的唯一应
     * 用标识（哈啰分配）
     */
    private final String appid;
    /**
     * 对方公钥
     */
    private final String publicKey;
    /**
     * 我方私钥
     */
    private final String privateKey;
    /**
     * 请求编码
     */
    private final String inputCharset;
    /**
     * 渠道标识
     */
    private final String channelSignature;
}
