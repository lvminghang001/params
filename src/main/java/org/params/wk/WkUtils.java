package org.params.wk;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 *  加解密工具类
 */
public class WkUtils {
    /**
     * 随机⽣成⼀个aesKey
     *
     * @return SecretKey
     * @throws Exception
     */
    public static String generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return Base64.getEncoder().encodeToString(keyGenerator.generateKey().getEncoded());
    }
    /**
     * 使⽤AES密钥加密传输数据
     *
     * @param jsonData json数据
     * @param aesKey 临时密钥
     * @return String
     * @throws Exception
     */
    public static String encryptData(String jsonData, String aesKey) throws
            Exception {
        SecretKeySpec key = new
                SecretKeySpec(Base64.getDecoder().decode(aesKey), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedJson =
                cipher.doFinal(jsonData.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedJson);
    }
    /**
     * 使⽤RSA公钥加密随机AES密钥
     *
     * @param publicRSAKey RSA公钥
     * @return String
     * @throws Exception
     */
    public static String encryptKey(String publicRSAKey, String aesKey) throws
            Exception {
        byte[] publicKeyData = Base64.getDecoder().decode(publicRSAKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(new
                X509EncodedKeySpec(publicKeyData));
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedKey =
                cipher.doFinal(Base64.getDecoder().decode(aesKey));
        return Base64.getEncoder().encodeToString(encryptedKey);
    }
    /**
     * 解密aesKey原⽂
     *
     * @param encryptedKey 加密的aesKey
     * @param privateRSAKey rsa私钥
     * @return String
     * @throws Exception
     */
    public static String decryptKey(String encryptedKey, String privateRSAKey)
            throws Exception {
        byte[] privateKeyData = Base64.getDecoder().decode(privateRSAKey);
        byte[] encryptedAESKey = Base64.getDecoder().decode(encryptedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(new
                PKCS8EncodedKeySpec(privateKeyData));
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedAESKey = cipher.doFinal(encryptedAESKey);
        return Base64.getEncoder().encodeToString(decryptedAESKey);
    }
    /**
     * RSA签名
     *
     * @param data 待签名数据
     * @param privateKeyStr 私钥
     * @return 签名
     * @throws Exception
     */
    public static String sign(String data, String privateKeyStr) throws
            Exception {
//创建PKCS8编码密钥规范
        byte[] priKey = Base64.getDecoder().decode(privateKeyStr);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(priKey);
//返回转换指定算法的KeyFactory对象
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//根据PKCS8编码密钥规范产⽣私钥对象
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
//⽤指定算法产⽣签名对象Signature
        Signature signature = Signature.getInstance("SHA1withRSA");
//⽤私钥初始化签名对象Signature
        signature.initSign(privateKey);
//将待签名的数据传送给签名对象(须在初始化之后)
        signature.update(data.getBytes(StandardCharsets.UTF_8));
//返回签名结果字节数组
        byte[] sign = signature.sign();
//返回Base64编码后的字符串
        return Base64.getEncoder().encodeToString(sign);
    }
    /**
     * RSA校验数字签名
     *
     * @param data 待校验数据
     * @param sign 数字签名
     * @param publicKeyStr 公钥
     * @return boolean 校验成功返回true，失败返回false
     */
    public static boolean verify(String data,String sign, String publicKeyStr)
            throws Exception {
//返回转换指定算法的KeyFactory对象
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//创建X509编码密钥规范
        byte[] pubKey = Base64.getDecoder().decode(publicKeyStr);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKey);
//根据X509编码密钥规范产⽣公钥对象
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
//⽤指定算法产⽣签名对象Signature
        Signature signature = Signature.getInstance("SHA1withRSA");
//⽤公钥初始化签名对象,⽤于验证签名
        signature.initVerify(publicKey);
//更新签名内容
        signature.update(data.getBytes(StandardCharsets.UTF_8));
//得到验证结果
        return signature.verify(Base64.getDecoder().decode(sign));
    }
    /**
     * 解密JSON数据
     *
     * @param encryptedData 加密JSON数据
     * @param encryptedKey 加密key
     * @param privateRSAKey 私钥
     * @return String
     * @throws Exception
     */
    public static String decryptData(String encryptedData, String
            encryptedKey, String privateRSAKey) throws Exception {
        byte[] encryptJsonData = Base64.getDecoder().decode(encryptedData);
        String aesKey = decryptKey(encryptedKey, privateRSAKey);
        SecretKeySpec secretKeySpec = new
                SecretKeySpec(Base64.getDecoder().decode(aesKey), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedJson = cipher.doFinal(encryptJsonData);
        return new String(decryptedJson, StandardCharsets.UTF_8);
    }

//    public void demo() throws Exception {
//// 微客⾦融公私钥
//// -------------------------微客⾦融--------------------------------
//// 参数
//        String data = "{\"phone\":\"92acdc89d5d9b2a62eb6ebeccb3045db\"}";
//// ⽣成AES key
//        String aesKey = generateAESKey();
//        System.out.println("⽣成随机密钥成功！原⽂：" + aesKey);
//// 参数AES加密参数
//        String encryptedData = encryptData(data, aesKey);
//        System.out.println("数据加密成功！密⽂：" + encryptedData);
//// AES key⽤产品⽅公钥加密
//        String encryptAesKey = encryptKey(orgPublicKeyStr, aesKey);
//        System.out.println("密钥加密成功！加密后为：" + encryptAesKey);
//// 组装请求参数
//        Map<String,Object> reqParamMap = new HashMap<>();
//        reqParamMap.put("requestNo", IdUtil.simpleUUID());
//        reqParamMap.put("channelCode", "cch001");
//        reqParamMap.put("data", encryptedData);
//        reqParamMap.put("key", encryptAesKey);
//        reqParamMap.put("timeStamp", System.currentTimeMillis());
//        String signStr = MapUtil.sortJoin(reqParamMap, "&", "=", true);
//        String sign = sign(signStr,privateKeyStr);
////增加签名参数
//        reqParamMap.put("sign", sign);
//        String ReqJson = JSONUtil.toJsonStr(reqParamMap);
//        System.out.println("请求参数:" + ReqJson);
//// -------------------------产品⽅--------------------------------
//// 验签
//        System.out.println("验证签名:" + verify(signStr,sign,publicKeyStr));
//// 解密秘钥
//        String aesSecurity = decryptKey(encryptAesKey,orgPrivateKeyStr);
//        System.out.println("解密后AES秘钥明⽂:" + aesSecurity);
//        System.out.println("解密后请求数据:" +
//                decryptData(encryptedData,encryptAesKey,orgPrivateKeyStr));
//        String responseData = "{\"result\": 1}";
//
//// 响应结果
//        Map<String,Object> respParamMap = new HashMap<>();
//        respParamMap.put("code", 200);
//        respParamMap.put("data", encryptData(responseData,aesSecurity));
//        respParamMap.put("msg", "请求成功");
//        System.out.println("响应接⼝数据：" + JSONUtil.toJsonStr(respParamMap));
//    }
}
