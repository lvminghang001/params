package org.params.oppo;

import com.alibaba.fastjson.JSON;
import org.params.yql.utils.AESUtil;
import org.params.yql.utils.AesUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OppoConstant {

//    /**
//     * 小安公私钥--测试
//     */
//    public static final String xaPubKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsIPBM37U4xHyaLVueUHzkasJz/LiDZkE7IyboLB5poVLE55FtlBQ4NEvd8l6ZGEkPTsOVEw5XWbP7q7DRo8nOJCq8ypskxE0nasU+fkvWasz5UuL5ZoScfQ4SDkK+OxhtUdSLXTvDrdrn+QsoC3sZHuPwu2LEbqDc+MLtKO+TpO+vQOodkSvyp4VoglwUQ6/9F1Rhi+fK7iPmgH2RepPSRl1GO9qLhv1uro+f+DYn2YtVVEWr2Wwik94RDPzCHd5mcrja5t0L8pVjNOTbK1MR2G1KuHUAGOrP/EEfu67kfwHP5KgWZ24fOVlSljkyUIG1ZA9LeQPej9qlTRwz6ZL6wIDAQAB";
//    public static final String xaPriKey="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCwg8EzftTjEfJotW55QfORqwnP8uINmQTsjJugsHmmhUsTnkW2UFDg0S93yXpkYSQ9Ow5UTDldZs/ursNGjyc4kKrzKmyTETSdqxT5+S9ZqzPlS4vlmhJx9DhIOQr47GG1R1ItdO8Ot2uf5CygLexke4/C7YsRuoNz4wu0o75Ok769A6h2RK/KnhWiCXBRDr/0XVGGL58ruI+aAfZF6k9JGXUY72ouG/W6uj5/4NifZi1VURavZbCKT3hEM/MId3mZyuNrm3QvylWM05NsrUxHYbUq4dQAY6s/8QR+7ruR/Ac/kqBZnbh85WVKWOTJQgbVkD0t5A96P2qVNHDPpkvrAgMBAAECggEASgu7LEL0QxzwN4SEh1FnP6AkSZt1wIaETkbdshbVsUkRKXWngMdIZkbaRsG6Ggm2DgmMBUjEd639Y/7j4+GBbnUhgUg2q6r4C3wNHJ+vgUoURwfd878iJbNxOLrVI37lrgnxKyh2cBH5j+VD4x3goHZE5FyJjjJTmnWQH4Lg/kzATmuUSo1vN7CvUXDlhw2AZXmtt9q+DFY73DN2PMSjK2aev1tWFaUpnegcEuhM8VsmZa1le0j6DWwQdQWSqoA5lNwR/sapsVw7TFrjDKC3gbPrTeTSfZhjW8T9UD9Dmr1vNHWdtv17xOfR9d52T7aTjcR+TvLK0Nd6rBaZqTfsoQKBgQDxzVAYEG/q/W9epHMJfCVk5IAaGt7WpF1g/ffo5OtpvIXSK+fwDwEykqoq7fG+BsdQ2rIM+tCtM/56e4LR+PZ0YYXCaZfpvxloZiriMG/PFJepGkdO/ygFFKwr8FkyHVNnB3dp5riKtHEd/Z7agVBDyVyEoQTEDJWH7/wyn1YfvQKBgQC64RB4dxkGwXzeYNlrQ7sjFsXVLx6rCZtk97+JAA08yB5JqcdS7aWzhXZzV31aMu9RR0vo9XGdoUZ4WrgicCIwMWazBxLFQNkJFLkDG/L0xtChaMQ7mvZvVvTnQhKwXECKAJAmDezwFFShAzJiWbP5wrZ+v3GV0H8OOogovGwgxwKBgQDqVKaCK6vKd8EuXwB8+Krkk+zxX4kFSlSUYouWUAM0fVbSz2n+4wuuDySKZf4Ywkj2unpCye7ha7n6ySeGhVeSgdA3EE4AYQomepykh3xTmwk/ABCLJguKoPYZvtsyDel9t2aqYsYE5cmZ3ufLqfY3OzVF8yXDOM7Q5mnqRTAZoQKBgGHFA+fybz5wlhKF2tnaepp1/JR27XWQNiNR1nwnccV98RKa53REbq+IQJtBh4xcH4aHZXiAiKtuR78Jv1INFmXeQpuhNLnMHoA4vj6ZLbfQGDCJ7jg873b6qHAgykRIT1+jwcaW/IHyMQB3+rEnuxgNPYr8P8GgbgiUqM2fHnbbAoGAPjvH1cpSYx5gj5lsrtSrTl6xd2NUUoTJKktBLMbUsHEusjigPYOzA+ov3H5ScDaDpblRon0E4QlEhQ7lYGgGYAzGLpTEVdLy9wXWpqUu5lxRjEtTr9P1Bd+rdMq9732PZJb2P+FtITxln7bV19dfhVVTQcubaWnvRba5GjFIs/A=";
//
//    /**
//     * oppo公私钥--测试
//     */
//    public static final String oppoPubKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCTaldZtXnmeZkUDkwIDaTquVzyosCmAKCNs+btZN3LxYbbRHPfK+fW/sIdcSNL7mvmHz+ibpAZwJSpiSLQSoXESxZxwqeGcrtCEdhcoTH6NYYEe0Aw3fUyuEWJz0nERWO58v9chNUYmYORhuyg5xaw0B5USMpIY7FNAQ1YW520BwIDAQAB";
//
//    //oppo渠道标识--测试
//    public static final String CHANNEL_SIGNATURE = "AyJfWJWzs9fnkVyvxCaNFw4LiMfJiulC1uH8rEuD5y";

    /**
     * 小安公私钥 --生产
     */
//    public static final String xaPubKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsIPBM37U4xHyaLVueUHzkasJz/LiDZkE7IyboLB5poVLE55FtlBQ4NEvd8l6ZGEkPTsOVEw5XWbP7q7DRo8nOJCq8ypskxE0nasU+fkvWasz5UuL5ZoScfQ4SDkK+OxhtUdSLXTvDrdrn+QsoC3sZHuPwu2LEbqDc+MLtKO+TpO+vQOodkSvyp4VoglwUQ6/9F1Rhi+fK7iPmgH2RepPSRl1GO9qLhv1uro+f+DYn2YtVVEWr2Wwik94RDPzCHd5mcrja5t0L8pVjNOTbK1MR2G1KuHUAGOrP/EEfu67kfwHP5KgWZ24fOVlSljkyUIG1ZA9LeQPej9qlTRwz6ZL6wIDAQAB";
//    public static final String xaPriKey="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCwg8EzftTjEfJotW55QfORqwnP8uINmQTsjJugsHmmhUsTnkW2UFDg0S93yXpkYSQ9Ow5UTDldZs/ursNGjyc4kKrzKmyTETSdqxT5+S9ZqzPlS4vlmhJx9DhIOQr47GG1R1ItdO8Ot2uf5CygLexke4/C7YsRuoNz4wu0o75Ok769A6h2RK/KnhWiCXBRDr/0XVGGL58ruI+aAfZF6k9JGXUY72ouG/W6uj5/4NifZi1VURavZbCKT3hEM/MId3mZyuNrm3QvylWM05NsrUxHYbUq4dQAY6s/8QR+7ruR/Ac/kqBZnbh85WVKWOTJQgbVkD0t5A96P2qVNHDPpkvrAgMBAAECggEASgu7LEL0QxzwN4SEh1FnP6AkSZt1wIaETkbdshbVsUkRKXWngMdIZkbaRsG6Ggm2DgmMBUjEd639Y/7j4+GBbnUhgUg2q6r4C3wNHJ+vgUoURwfd878iJbNxOLrVI37lrgnxKyh2cBH5j+VD4x3goHZE5FyJjjJTmnWQH4Lg/kzATmuUSo1vN7CvUXDlhw2AZXmtt9q+DFY73DN2PMSjK2aev1tWFaUpnegcEuhM8VsmZa1le0j6DWwQdQWSqoA5lNwR/sapsVw7TFrjDKC3gbPrTeTSfZhjW8T9UD9Dmr1vNHWdtv17xOfR9d52T7aTjcR+TvLK0Nd6rBaZqTfsoQKBgQDxzVAYEG/q/W9epHMJfCVk5IAaGt7WpF1g/ffo5OtpvIXSK+fwDwEykqoq7fG+BsdQ2rIM+tCtM/56e4LR+PZ0YYXCaZfpvxloZiriMG/PFJepGkdO/ygFFKwr8FkyHVNnB3dp5riKtHEd/Z7agVBDyVyEoQTEDJWH7/wyn1YfvQKBgQC64RB4dxkGwXzeYNlrQ7sjFsXVLx6rCZtk97+JAA08yB5JqcdS7aWzhXZzV31aMu9RR0vo9XGdoUZ4WrgicCIwMWazBxLFQNkJFLkDG/L0xtChaMQ7mvZvVvTnQhKwXECKAJAmDezwFFShAzJiWbP5wrZ+v3GV0H8OOogovGwgxwKBgQDqVKaCK6vKd8EuXwB8+Krkk+zxX4kFSlSUYouWUAM0fVbSz2n+4wuuDySKZf4Ywkj2unpCye7ha7n6ySeGhVeSgdA3EE4AYQomepykh3xTmwk/ABCLJguKoPYZvtsyDel9t2aqYsYE5cmZ3ufLqfY3OzVF8yXDOM7Q5mnqRTAZoQKBgGHFA+fybz5wlhKF2tnaepp1/JR27XWQNiNR1nwnccV98RKa53REbq+IQJtBh4xcH4aHZXiAiKtuR78Jv1INFmXeQpuhNLnMHoA4vj6ZLbfQGDCJ7jg873b6qHAgykRIT1+jwcaW/IHyMQB3+rEnuxgNPYr8P8GgbgiUqM2fHnbbAoGAPjvH1cpSYx5gj5lsrtSrTl6xd2NUUoTJKktBLMbUsHEusjigPYOzA+ov3H5ScDaDpblRon0E4QlEhQ7lYGgGYAzGLpTEVdLy9wXWpqUu5lxRjEtTr9P1Bd+rdMq9732PZJb2P+FtITxln7bV19dfhVVTQcubaWnvRba5GjFIs/A=";

    /**
     *  测试test环境
     */
    public static final String ryhPubKeyTest="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDdcWEP6RZTVoN3BAP87bUNLW+klqFaDX5k8JdMDd9ndhvfObFtOgMI8y3MKmYDnQ0K5auAgnsZkAX2OCVguhF/6ybUXdi69Vg3xbYANFJmj2oHkx7WiCgyiJ52cOt8TjNKc+G/ItttXqw5isJMt6oO7EMPPLwaeQCXiownQmTctQIDAQAB";
    public static final String ryhPriKeyTest="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAN1xYQ/pFlNWg3cEA/zttQ0tb6SWoVoNfmTwl0wN32d2G985sW06AwjzLcwqZgOdDQrlq4CCexmQBfY4JWC6EX/rJtRd2Lr1WDfFtgA0UmaPageTHtaIKDKInnZw63xOM0pz4b8i221erDmKwky3qg7sQw88vBp5AJeKjCdCZNy1AgMBAAECgYBXlnrv0iAUgompT5tMNbGBLtGT5kiX1/KAdnFBkdMllx/bIXS+YU3GAnnA4ZxMHZiFEObPA80xBTh2dSrDoZU9qOPke+0Hsp6kRMswkLz9/sZDBzRSPqdyATMKCFd0dzmGfJWzsw7QWDvanFuHwUdfqVMxd8wnL0kQTlXmrvJigQJBAPs2054u+ViHLSuy+cYEHJ5OysKp9ocl+j/WOEYff6kYZPHl1J+lhnHktElb9krKkXZYnAv/fb9dEZdMGlWd7dECQQDhqVuWgXJzpqVSOGFjNbXtCPgktBDR9qif9I2dM/N+RglqPaV+OmQ8/Q8FQT44vMwLkKG8KrKdjLHI66lofYWlAkBE6Kgptubs0r+gQgrjt1MGVcSmmyqlrdQ0WJW9HnzNLITcGdg2Jph3e228xb4Mi/5UwT+kxuN+b5AYuWZr39NRAkBpNmk5dwlB2sTh3N+rPfOgnLn1gu8EthhQ29jbHsm8ajMhkbSDcf4iYWqLbKvyEDSif8Co1s+InXyWzSmjSkLxAkEAx+gjsZUj7s6bF3qnBW484qZvY0aGKC8anwMq2XWY6Jt6x62LuPR6ZtNuURtZvPBncN7NZWGRKg6WxWDnDvkQEw==";

    /**
     *  生产配置
     */
    public static final String ryhPubKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKqqpwjE7Nfmdp7j0Xz2CJIWJuCELY1I7OG3THNyyc/T0s4SHE5QKwZ0QZpZ8LdplLxXFRK1GtRFvKtzuoC2QMxfiiGpQ+HUszIjlt5Pp1VfnLbQgg1vkX6XzS40DV2KLD+vRsrm5Lwz17SMK+9OZlD9nScbX7E+/gl4ZLGdmbFQIDAQAB";
    public static final String ryhPriKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIqqqnCMTs1+Z2nuPRfPYIkhYm4IQtjUjs4bdMc3LJz9PSzhIcTlArBnRBmlnwt2mUvFcVErUa1EW8q3O6gLZAzF+KIalD4dSzMiOW3k+nVV+cttCCDW+RfpfNLjQNXYosP69GyubkvDPXtIwr705mUP2dJxtfsT7+CXhksZ2ZsVAgMBAAECgYBiOwhGdn6UgAkON6C7r0J5olayD9Qa+mi3hSeywFkwVQzzMADWFE1VXbIWYQdL/ZYW4SWnFILltVk5P5hZMMiMYBdExKePieg3tdEfm6BUk4Mx2g0Gy00nBMoumADVywnzRVGi3GozoyyTcLaA7/Lk47ojuYhQDqEXrs63J+iYgQJBAMxaMktkKhOtjB9jrDphcpE1c6gXBvnieWjfoUuKF4ngGwJp1KiKR99J0hL0UQrQ2k9sq8JMd4+zPBCk2/PYxoUCQQCttoo2JW9VMbLM1fgeud/BLxyB4ocp0AOh25WQLHMYZz6mqYnL1PNDYOdp6lgK8w1He/y5bPfFj+08rhNYbQ9RAkEAn0+h7gyEkRq3JrF5z3mw62uUYvKwyZa5t/y/rjhV6XMBG3yOAWbsaTMPxx8ZHyl7SHgb3znl6+17Yi5kG1JgUQJACgOV4B00V9LvncNP9GV+sqJGoG4woIZvTytbad5GHEgHYpAvc2KpS18QuBhcTvVH9LD/Fqljdy4e3Pqj/tr6oQJAdwM6cffcfF1ytbN3nrHhKVmhVT75KZ8auyPIsdKMilMIkBxKTjNbUZyrxUgXS4aMc0s66cDFhYwTffiZn1Ba3w==";


    /**
     * oppo公私钥--生产
     */
//    public static final String oppoPubKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKqqpwjE7Nfmdp7j0Xz2CJIWJuCELY1I7OG3THNyyc/T0s4SHE5QKwZ0QZpZ8LdplLxXFRK1GtRFvKtzuoC2QMxfiiGpQ+HUszIjlt5Pp1VfnLbQgg1vkX6XzS40DV2KLD+vRsrm5Lwz17SMK+9OZlD9nScbX7E+/gl4ZLGdmbFQIDAQAB";
    /**
     * oppo公私钥--测试
     */
  //   public static final String oppoPubKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA14WTUF8mbcdog1Tff4v6Ecod5zVPvDmmW0UxdCzs3QIQwr7eCq9RVSlomaGuT7GarEqMDSP+f8+xXG27qaP8jwv0h9MGqaMAtUkjRIQ46UMnl7XQp3k2U1svH/g4eyabbIIh5fLlB25Z1GI+nJ/1yJDu6Z8uZ73YXhR1ed8EkxwIHyLN236DZqWt+Zfo94h+4/Ru5G+JxvWZjGDj5lB4yub9JSAHbSbAP26QI7u3p9X6YFjVeoEYwgoV8BaR4NKDwbF86w2BtA4gDJsfATwmFczESZIgWkHr3yEfCdtEQvVFn9XvOoX4sPx8CNljTNajlCCbt2prRnhO3BNSrcxy9QIDAQAB";




    //oppo渠道标识--测试test
//    public static final String CHANNEL_SIGNATURE = "h1Tmptvy4HZL1fKe5cbRe8sdKGXb2JcKfRmjdCcMUy";

    //oppo渠道标识--生产
    public static final String CHANNEL_SIGNATURE = "W0QAGAdH9lqt7Xk5B3HMi6jWIYJ8kZF0KMa6zUJbjU";

    //联登注册
    public static final String LONGIN_METHOD = "union.login";
    //获取协议
    public static final String AGREEMENT_QUERY = "agreement.query";


    /**
     * name	String	是	协议名称
     * type	String	否	协议类型
     * content	String	是	协议内容
     * textType	String	是	内容类型	URL/HTML
     * defaultChosen	Boolean	是	是否默认选择	默认false
     * forceRead	Boolean	是	是否强制阅读	默认false
     * @return
     */
    public static List<Map<String,Object>> getAgreements() {
        Map<String,Object>obj1=new HashMap<>();
        obj1.put("name","注册协议");
        obj1.put("content","https://ramljykqhb.rongyouhua.com/html/agreement/registerAgreement.html");
        obj1.put("textType","URL");
        obj1.put("defaultChosen",false);
        obj1.put("forceRead",false);

        Map<String,Object>obj2=new HashMap<>();
        obj2.put("name","隐私协议");
        obj2.put("content","https://ramljykqhb.rongyouhua.com/html/agreement/privacyAgreement.html");
        obj2.put("textType","URL");
        obj2.put("defaultChosen",false);
        obj2.put("forceRead",false);

        List<Map<String,Object>> list=new ArrayList<>();
        list.add(obj1);
        list.add(obj2);
        return list;

    }

    /**
     * {
     *     "method": "union.login",
     *     "appId": "RYH",
     *     "sign": "",
     *     "flowNo": "RYH82464133366030162540873340",
     *     "params": "",
     *     "version": "1.0",
     *     "key": "LKEZLyH3+5wQ/LNSrgS7SxWjEIvPFX1il2RVpUvpMoIrLoGAMZ7Q1YKPU7/LUpe5h3QycCZ5uxr5t9KIuW+FazP9jUVJVzxQm63o6U+pVYWkKDlo7El6OtGcbzHnhGbQlExhEz+Xry31sRkOTS0XVxCpMEJZk6hdsy70BiI0PU0=",
     *     "timestamp": 1721962340216
     * }
     * @param args
     */


//    public static String getAESKey() {
//        try {
//            KeyGenerator kg = KeyGenerator.getInstance("AES");
//            kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256
//            SecretKey sk = kg.generateKey();
//            byte[] b = sk.getEncoded();
//            return parseByte2HexStr(b);
//        } catch (Exception e) {
//            System.out.println("生成AESKEY异常:" + e.getMessage());
//        }
//        return "";
//    }
}
