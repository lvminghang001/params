package org.params.common.utils;


import com.alibaba.fastjson.JSONObject;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class CommonRsaUtil {

    //定义加密方式
    public static final String KEY_RSA = "RSA";
    //定义公钥关键词
    public static final String KEY_RSA_PUBLIC_KEY = "RSAPublicKey";
    //定义私钥关键词
    public static final String KEY_RSA_PRIVATE_KEY = "RSAPrivateKey";
    //定义签名算法
    private final static String KEY_RSA_SIGNATURE = "MD5withRSA";
    //RSA最大加密大小
    private final static int MAX_ENCRYPT_BLOCK = 117;
    //RSA最大解密大小
    private final static int MAX_DECRYPT_BLOCK = 128;
    public final static String sss_private = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALYRDC71/XNyjDaIZf4NXeJ292FG3Q+N8Z/+UHPcgE39Ffxvy3AOIS+GB0jvcSDoYN55Vqwgt0e6TXERw8jpwfOWWfCW5z95yzbB3rpALKjp6AAmb6ZSJNaSI50At1+4lDckVmi3dw+ltLBoy3z4onbWGQhnA64R/5ww7gAJ4LLNAgMBAAECgYBunr8DTWy4F1H/1DH8AQ0ZCuVVUmQADmNPrYoeS0wdlnckUJyPsdYR9OI6O1lGAErTAXBa1unlB9oDqCKH0ReIq/GjHw1arvuR6Ayh+KhtwHHDCOga7FH7JprqEUwgDApeverda5zSkDKrAcpHTAbl7MW4QFPKAyZ0tc3uiYgktQJBAO/6XYpq/S2mM6I0WoVjd5i+LvIAjwHLR4uh/7o58Gmer/sdFv7+QUMMT6Nbwm6Td2xm9glGCdOWSIdS1PDO1PMCQQDCOOCV6sHpqYDuPDYZcf4Dgm0ht8x7oY+94F5QW35QGCgNuq1C6HCXWwJOPuSJ4JfD4prUa+tRWt5hoNl2+0k/AkEAmcypupaLIICOQvyuryJqedgZmjvFa27usYznqmCLtgVf395q6I3nIaN4PsgGOnwEY9MxCAx9K7/7R89MbfEBBQJBAJ462D1K1FAY2AE+7JQuWlNhnqcppl3ScdivQicVYwK9q2QEAcPQummUq3wzoNzzLLFZm+oTQdM1xJalBIX6kuECQQDrjz1D8wzHtT0c7mXvnyakEnXjjFGkOymhtx6NVIVSbCYBMtRnQt39mFfxpJ/c/QkWGqUcJAYbD6pru8GCpBtW";
    public final static String sss_public = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2EQwu9f1zcow2iGX+DV3idvdhRt0PjfGf/lBz3IBN/RX8b8twDiEvhgdI73Eg6GDeeVasILdHuk1xEcPI6cHzllnwluc/ecs2wd66QCyo6egAJm+mUiTWkiOdALdfuJQ3JFZot3cPpbSwaMt8+KJ21hkIZwOuEf+cMO4ACeCyzQIDAQAB";

    /**
     *   模拟请求过程
     *   请求方： 使用三方公钥加密数据，我方私钥签名。
     *   接收方： 使用请求方公钥验签名，使用己方私钥解密数据。
     */
    public static void mockRsaRequest(){
        //生成公私钥
        Map<String, Object> xaKeyMap = createKey();
        String publicKeyXa = getPublicKey(xaKeyMap);
        String privateKeyXa = getPrivateKey(xaKeyMap);
        System.out.println("公钥"+publicKeyXa);
        System.out.println("私钥"+privateKeyXa);
        Map<String, Object> thirdKeyMap = createKey();
        String publicKeyThird = getPublicKey(thirdKeyMap);
        String privateKeyThird = getPrivateKey(thirdKeyMap);

        //参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "张三");
        jsonObject.put("age", "18");
        jsonObject.put("house", "2");
        jsonObject.put("car", "1");
        String requestJsonStr = jsonObject.toJSONString();

        //请求方:三方公钥加密，己方私钥签名
        //验签方:三方公钥验签，己方私钥解密

        System.out.println("============================================请求开始======================================================================");
        //请求三方, 三方公钥加密， 私钥加签
        String encryptByXa = encryptByPublicKey(requestJsonStr, publicKeyThird);
        // 产生签名
        String signXa = sign(encryptByXa, privateKeyXa);
        System.out.println("请求签名:" + signXa);
        System.out.println("============================================请求结束======================================================================");
        System.out.println("============================================三方解签开始======================================================================");
        //三方解析: 公钥验签， 己方私钥解密
        //验证签名
        boolean verify = verify(encryptByXa, publicKeyXa, signXa);
        System.out.println("三方验证签名:" + verify);

        String decryptByPrivateKey = decryptByPrivateKey(encryptByXa, privateKeyThird);
        System.out.println("三方私钥加密：" + decryptByPrivateKey);
        System.out.println("============================================三方解签结束======================================================================");



        System.out.println("============================================三方请求开始======================================================================");
        String encryptByThird = encryptByPublicKey(requestJsonStr, publicKeyXa);
        // 产生签名
        String signThird = sign(encryptByThird, privateKeyThird);
        System.out.println("三方请求签名:" + signThird);
        System.out.println("============================================三方请求结束======================================================================");
        System.out.println("============================================解签开始======================================================================");
        //验证签名
        boolean verifyXa = verify(encryptByThird, publicKeyThird, signThird);
        System.out.println("验证签名:" + verifyXa);

        String decryptByXa = decryptByPrivateKey(encryptByThird, privateKeyXa);
        System.out.println("私钥加密：" + decryptByXa);

        System.out.println("============================================解签结束======================================================================");

        String encryptByThird2 = encryptByPublicKey(requestJsonStr, sss_public);
        System.out.println("测试数据:" + encryptByThird2);
//        String sign = sign(encryptByThird2, ceshi_public);
//        System.out.println("测试加签:" + sign);
        String data = "LcDTvLux/98+sMXbBFlrqyK1FtVfBbW2TW+okRR4lHz1lZTzr80oF92eSMmMyEzXGPKIX/QVJ38OHgwn3PnhoCtyqLXE+CI7wy9fibHfS6x9VwmieLgX97gyID97ZRM1Rps8dh6Iwmmwt3De5+Mt3oDgrnsMPB23D+QzTSXYohRNm4plNL1o7bPPJwyKCW8hGqMnDaQOeCKDCklXfwEi+VvxZqVm2mOMBHE0RAUEumNP7GU4i/o4t6zVoYzKLHZTXCSUbFj0tp4QdXW8uH9Wmyl55uO8WXnfksTXVEgMNQkiTc559MGwhztxdtHjNuPI1P2szecPLcY1tR47WlIl4w==";
        String ssss = decryptByPrivateKey(data, sss_private);
        System.out.println("测试解密:" + ssss);
    }

    public static void main(String[] args) {
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL/GdzcW1EHbYdy5/eRjOzVAlZQydPMYSolYrEUg416+NJw0APRWFU4R6kZIDsM4HvPVYUaQ/ONloSJ8eWincBQAzNdwTKjTU70ST1bF3kARvs5+N+ne8efcQZwDwkFeJDfjtUCr/NTAK7RGBW+wKFRKwB4gtifhFmW7B5zPeUbdAgMBAAECgYApJr5ILPbpFtleEu8fRgfbentebKFZ1LmR1VxKDS+dEfWaGzKlO5+gvAlogWfhjjZeHkicatpJNXKpQsqOoXdUBF9aKCNjLg772VVfqBrmyas0lDaGJy9SLxepFFYhPSWFHwzuHP2mwSB9izR56+HJ6c5mXy90GOkh9GjxPZeZ4QJBAOTwFffPcSRcmss+9CA4h4MgFlOLja8JrkVcSGMyg9i4QK62TPTxYRia7nvT+0YKUnEV7wKDeAZ47qq69CsVFekCQQDWccx+RJ25z9VlrcHhb8j3cAWrvIANzYNl94JWk9NhABXHbSL5OEYvfSL/sZtv20/vB8Q7ma3KUYoc+J5BmizVAkEAg73I1nawI/2KMiVC9HuzN3iygFcRLEXE3DMTkMnT51V4G1HajSWN+0vEqss8kHyGHn9Br8+ZtRfIN8UnltcmaQJBALIzBre21UrfRnmkFoT7m4ji8hNwJQlTSmmlcA358aGdrHt4WaO3+7rixf6s3HnrSVVcotjUL/L8J0VD5F6ne7ECQCQa99zOcuTo56McEzQE3TaP+s3/u34/p3uWfD1y/BDIG/e6WVXrDwyLNAVQh1opoV+ZICsOBcIWaPVAbmy4m6U=";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/xnc3FtRB22Hcuf3kYzs1QJWUMnTzGEqJWKxFIONevjScNAD0VhVOEepGSA7DOB7z1WFGkPzjZaEifHlop3AUAMzXcEyo01O9Ek9Wxd5AEb7Ofjfp3vHn3EGcA8JBXiQ347VAq/zUwCu0RgVvsChUSsAeILYn4RZluwecz3lG3QIDAQAB";
//        mockRsaRequest();
        String signXa = sign("test", privateKey);
        System.out.println(verify("test", publicKey, signXa));
    }

    /**
     * 生成公私密钥对
     */
    public static Map<String, Object> createKey() {
        Map<String, Object> map = null;
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_RSA);
            //设置密钥对的bit数，越大越安全，但速度减慢，一般使用512或1024
            generator.initialize(1024);
            KeyPair keyPair = generator.generateKeyPair();
            // 获取公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 获取私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 将密钥对封装为Map
            map = new HashMap<>();
            map.put(KEY_RSA_PUBLIC_KEY, publicKey);
            map.put(KEY_RSA_PRIVATE_KEY, privateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 获取Base64编码的公钥字符串
     */
    public static String getPublicKey(Map<String, Object> map) {
        Key key = (Key) map.get(KEY_RSA_PUBLIC_KEY);
        return encryptBase64(key.getEncoded());
    }

    /**
     * 获取Base64编码的私钥字符串
     */
    public static String getPrivateKey(Map<String, Object> map) {
        Key key = (Key) map.get(KEY_RSA_PRIVATE_KEY);
        return encryptBase64(key.getEncoded());
    }

    /**
     * BASE64 编码
     * @param key 需要Base64编码的字节数组
     * @return 字符串
     */
    public static String encryptBase64(byte[] key) {
        return new String(Base64.getEncoder().encode(key));
    }

    /**
     * BASE64 解码
     * @param key 需要Base64解码的字符串
     * @return 字节数组
     */
    public static byte[] decryptBase64(String key) {
        return Base64.getDecoder().decode(key);
    }


    /**
     * 公钥加密
     *
     * @param encryptingStr
     * @param publicKeyStr
     * @return
     */
    public static String encryptByPublicKey(String encryptingStr, String publicKeyStr) {
        try {
            // 将公钥由字符串转为UTF-8格式的字节数组
            byte[] publicKeyBytes = decryptBase64(publicKeyStr);
            // 获得公钥
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            // 取得待加密数据
            byte[] data = encryptingStr.getBytes("UTF-8");
            KeyFactory factory;
            factory = KeyFactory.getInstance(KEY_RSA);
            PublicKey publicKey = factory.generatePublic(keySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 返回加密后由Base64编码的加密信息
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return encryptBase64(decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 私钥加密
     * @param encryptingStr
     * @param privateKeyStr
     * @return
     */
    public static String encryptByPrivateKey(String encryptingStr, String privateKeyStr) {
        try {
            byte[] privateKeyBytes = decryptBase64(privateKeyStr);
            // 获得私钥
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            // 取得待加密数据
            byte[] data = encryptingStr.getBytes("UTF-8");
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PrivateKey privateKey = factory.generatePrivate(keySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            // 返回加密后由Base64编码的加密信息
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return encryptBase64(decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 公钥解密
     * @param encryptedStr
     * @param publicKeyStr
     * @return
     */
    public static String decryptByPublicKey(String encryptedStr, String publicKeyStr) {
        try {
            // 对公钥解密
            byte[] publicKeyBytes = decryptBase64(publicKeyStr);
            // 取得公钥
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            // 取得待加密数据
            byte[] data = decryptBase64(encryptedStr);
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PublicKey publicKey = factory.generatePublic(keySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            // 返回UTF-8编码的解密信息
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 私钥解密
     *
     * @param encryptedStr 待解密数据
     * @param
     * @return
     */
    public static String decryptByPrivateKey(String encryptedStr,String privateKeyStr) {
        try {
            // 对私钥解密
            byte[] privateKeyBytes = decryptBase64(privateKeyStr);
            // 获得私钥
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            // 获得待解密数据
            byte[] data = decryptBase64(encryptedStr);
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PrivateKey privateKey = factory.generatePrivate(keySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // 返回UTF-8编码的解密信息
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 用私钥对加密数据进行签名
     *
     * @param encryptedStr
     * @param privateKey
     * @return
     */
    public static String sign(String encryptedStr, String privateKey) {
        String str = "";
        try {
            //将私钥加密数据字符串转换为字节数组
            byte[] data = encryptedStr.getBytes();
            // 解密由base64编码的私钥
            byte[] bytes = decryptBase64(privateKey);
            // 构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec pkcs = new PKCS8EncodedKeySpec(bytes);
            // 指定的加密算法
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            // 取私钥对象
            PrivateKey key = factory.generatePrivate(pkcs);
            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
            signature.initSign(key);
            signature.update(data);
            str = encryptBase64(signature.sign());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 校验数字签名
     *
     * @param encryptedStr
     * @param publicKey
     * @param sign
     * @return 校验成功返回true，失败返回false
     */
    public static boolean verify(String encryptedStr, String publicKey, String sign) {
        boolean flag = false;
        try {
            //将私钥加密数据字符串转换为字节数组
            byte[] data = encryptedStr.getBytes();
            // 解密由base64编码的公钥
            byte[] bytes = decryptBase64(publicKey);
            // 构造X509EncodedKeySpec对象
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
            // 指定的加密算法
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            // 取公钥对象
            PublicKey key = factory.generatePublic(keySpec);
            // 用公钥验证数字签名
            Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
            signature.initVerify(key);
            signature.update(data);
            flag = signature.verify(decryptBase64(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
