package org.params.wqb;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.params.common.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

@Slf4j
public class HalfApiLoanCommonRSAUtil {

    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;
    private static final String CHARSET = "utf-8";
    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    public static void main(String[] args) {
        // 对方的公钥，用于加密(测试环境默认都用一样的)
        String thirdPublicKey = WqbConfigEnum.PRO.getPublicKey();

        // 己方的私钥，用于解密(测试环境默认都用一样的)
        String selfPrivateKey = WqbConfigEnum.PRO.getPrivateKey();
        // 请求明文
        String requestData = "{\"aliveAuthImageUrl\":\"https://file.grjianr.com/xiaowei/ocr/20241105/R31c2a5cadeb94ad8a765c27f392d6a00.png\",\"aliveAuthTime\":\"2024-11-05 16:34:05\",\"applyNo\":\"LYX2024111117313130641039697\",\"companyName\":\"浙江省杭州市西湖区公安局\",\"contactName\":\"张大\",\"contactPhone\":\"13688887777\",\"contactRelationship\":\"04\",\"credentialsInvalidDate\":\"2026-09-20\",\"credentialsIssueDate\":\"2021-04-19\",\"deviceType\":\"1\",\"faceScore\":\"94\",\"gender\":\"M\",\"highestEducationalLevel\":\"02\",\"idAddress\":\"富土康售后服务3部\",\"idCardBackImageUrl\":\"https://file.grjianr.com/xiaowei/ocr/20241105/R556a962b3f3148bdb0b55f5582d743e7.jpeg\",\"idCardFrontImageUrl\":\"https://file.grjianr.com/xiaowei/ocr/20241105/R232a091ae7f94372865babbefafb0d38.jpeg\",\"idCardMd5\":\"4cdfe6a1aee9be9c0425621cd571c524\",\"idCityName\":\"杭州市\",\"idCityNameCode\":\"330100\",\"idProvinceCode\":\"330000\",\"idProvinceName\":\"浙江省\",\"idcard\":\"330106200310290557\",\"income\":\"10000\",\"ip\":\"39.171.240.225\",\"issuingOrgan\":\"日本市福岛县\",\"kinName\":\"张小\",\"kinPhone\":\"13699998888\",\"kinShip\":\"00\",\"latitude\":\"30.323920\",\"liveAddress\":\"浙江省杭州市西湖区公安局\",\"liveAreaCode\":\"330106\",\"liveAreaName\":\"西湖区\",\"liveCityCode\":\"330100\",\"liveCityName\":\"杭州市\",\"liveProvinceCode\":\"330000\",\"liveProvinceName\":\"浙江省\",\"longitude\":\"120.106061\",\"marriageStatus\":\"00\",\"name\":\"张三\",\"nation\":\"汉\",\"occupation\":\"00\",\"phone\":\"18248612087\",\"phoneMd5\":\"a3db2c7a688ca16d96c4948979dd755b\",\"workAddress\":\"浙江省杭州市西湖区公安局\",\"workAreaCode\":\"330106\",\"workAreaName\":\"西湖区\",\"workCityCode\":\"330100\",\"workCityName\":\"杭州市\",\"workProvinceCode\":\"330000\",\"workProvinceName\":\"浙江省\",\"workUnit\":\"0\"}";


        // 1.加密加签，构建请求
        JSONObject request = encryptionAndSignAndBuild(requestData, thirdPublicKey);

        // 2.解密验签，获取明文data
        String decrypt = decryptAndGetData(request, selfPrivateKey);

        System.out.println("请求的明文data:" + requestData);
        System.out.println("加密加签之后的request:" + request);
        System.out.println("解密出来的data:" + JSONObject.parseObject(decrypt));
    }

    /**
     * 加密加签，构建请求/返回
     *
     * @param requestData    请求/响应的明文data
     * @param thirdPublicKey 对方的公钥
     * @return 加密加签之后的请求体
     */
    public static JSONObject encryptionAndSignAndBuild(String requestData, String thirdPublicKey) {
        log.info("加密 请求明文data:{}", requestData);
        try {
            //获取随机密钥R
            String randomKey = getAESRandomKey();
//            log.info("随机密钥明文randomKey:{}", randomKey);

            //使用随机密钥R对明文A进行AES对称加密, 得到密文data
            String data = encryptAES(requestData, randomKey, CHARSET);
//            log.info("请求密文data:{}", data);

            String key = encryptRSA(randomKey, thirdPublicKey, CHARSET);
//            log.info("随机密钥密文key:{}", key);

            String requestBodyMd5 = genMD5(requestData);
//            log.info("请求明文MD5加密:{}", requestBodyMd5);

            //加签
            String sign = encryptRSA(requestBodyMd5, thirdPublicKey, CHARSET);
//            log.info("签名sign:{}", sign);

            JSONObject requestJson = new JSONObject();
            requestJson.put("data", data);
            requestJson.put("sign", sign);
            requestJson.put("key", key);
            requestJson.put("timestamp", System.currentTimeMillis());
            log.info("加密结束 requestJson:{}", requestJson);
            return requestJson;
        } catch (Exception e) {
            log.error("exception:{}", e.getMessage(), e);
        }
        return new JSONObject();
    }

    /**
     * 解密验签，获取请求/返回明文data
     *
     * @param requestJson    请求json
     * @param selfPrivateKey 己方的私钥
     * @return 明文data
     */
    public static String decryptAndGetData(JSONObject requestJson, String selfPrivateKey) {
        log.info("解密 密文requestJson:{}", requestJson);
        String data = requestJson.getString("data");//密文data
        String sign = requestJson.getString("sign");//签名sign
        String key = requestJson.getString("key");//加密密钥key

        if (StringUtils.isEmpty(data) || StringUtils.isEmpty(sign) || StringUtils.isEmpty(key)) {
            return null;
        }

        try {
            String R = decryptRSA(key, selfPrivateKey, CHARSET);
//            log.info("解密后随机密钥R:{}", R);

            //用解密得到的随机密钥明文对密文data进行解密
            String decryptData = decryptAES(data, R, CHARSET);
//            log.info("随机密钥R:{} ，解密明文:{}", R, decryptData);

            //验签
            //对解密出来的decryptData明文进行MD5处理得到摘要A
            String abstractA = genMD5(decryptData);
//            log.info("对明文A1进行MD5加密得到摘要A:{}", abstractA);

            //用己方私钥对签名sign进行解密得到摘要B
            String abstractB = decryptRSA(sign, selfPrivateKey, CHARSET);
//            log.info("对签名S进行解密得到摘要B:{}", abstractB);

            //判断C和B1是否一致，一致则通过验签
            if (!abstractA.equals(abstractB)) {
                log.info("验签失败");
                throw new RuntimeException("验签失败");
            }

            log.info("解密结束 decryptData:{}", decryptData);
            return decryptData;
        } catch (Exception e) {
            log.error("解密异常 exception:{}", e.getMessage(), e);
        }

        return null;
    }


    /**
     * RSA非对称
     * 加密
     *
     * @param content      待加密明文
     * @param publicKey    对方公钥
     * @param inputCharset 编码
     */
    public static String encryptRSA(String content, String publicKey, String inputCharset) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] encodedKey = Base64.decodeBase64(publicKey);
        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, pubKey);
        InputStream ins = new ByteArrayInputStream(content.getBytes(inputCharset));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        int bufLength;
        byte[] block;
        for (byte[] buf = new byte[MAX_ENCRYPT_BLOCK]; (bufLength = ins.read(buf)) != -1; writer.write(cipher.doFinal(block))) {
            if (buf.length == bufLength) {
                block = buf;
            } else {
                block = new byte[bufLength];
                System.arraycopy(buf, 0, block, 0, bufLength);
            }
        }
        return new String(Base64.encodeBase64(writer.toByteArray()), inputCharset);
    }

    /**
     * RSA非对称
     * 解密
     *
     * @param content      待解密密文
     * @param privateKey   己方私钥
     * @param inputCharset 编码
     */
    public static String decryptRSA(String content, String privateKey, String inputCharset) throws Exception {
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        KeyFactory keyF = KeyFactory.getInstance("RSA");
        PrivateKey priKey = keyF.generatePrivate(priPKCS8);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, priKey);
        InputStream ins = new ByteArrayInputStream(Base64.decodeBase64(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        int bufLength;
        byte[] block;
        for (byte[] buf = new byte[MAX_DECRYPT_BLOCK]; (bufLength = ins.read(buf)) != -1; writer.write(cipher.doFinal(block))) {
            if (buf.length == bufLength) {
                block = buf;
            } else {
                block = new byte[bufLength];
                System.arraycopy(buf, 0, block, 0, bufLength);
            }
        }
        return writer.toString(inputCharset);
    }

    /**
     * AES加密
     *
     * @param data         待加密明文
     * @param key          密钥
     * @param inputCharset 编码
     */
    public static String encryptAES(String data, String key, String inputCharset) throws Exception {
        Key k = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(1, k);
        return Base64.encodeBase64String(cipher.doFinal(data.getBytes(inputCharset)));
    }

    /**
     * AES解密
     *
     * @param data         待解密密文
     * @param key          密钥
     * @param inputCharset 编码
     */
    public static String decryptAES(String data, String key, String inputCharset) throws Exception {
        Key k = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(2, k);
        return new String(cipher.doFinal(Base64.decodeBase64(data)), inputCharset);
    }

    /**
     * MD5加密
     *
     * @param value 待加密字符串
     */
    public static String genMD5(String value) {
        return DigestUtils.md5Hex(value).toUpperCase();
    }

    /**
     * 获取随机密钥明文
     */
    public static String getAESRandomKey() {
        Random random = new Random();
        long longValue = random.nextLong();
        return String.format("%016x", longValue);
    }

}
