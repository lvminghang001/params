package org.params.weiX.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.params.weiX.dto.EncryptDTO;
import org.params.weiX.dto.WeiXParamsDto;
import org.params.weiX.exception.SignException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 产品平台改造加解密助手
 * </p>
 *
 * @author Ehine
 * @since 2020/6/20
 */
@Slf4j
@UtilityClass
public class ProductPlatformCryptoHelper {
    /**
     * UTF_8标识
     */
    private static final String UTF_8 = "utf8";

    private static final String KEY_ALGORTHM = "RSA";

    /**
     * 加密块大小 - 如果内容大于117字节需要分段加密
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 创建RSA公钥和私钥对
     * <p>
     * 公钥：RSAUtils.PUBLIC_KEY
     * 私钥：RSAUtils.PRIVATE_KEY
     *
     * @return Map
     * @throws NoSuchAlgorithmException 创建异常
     */
    public static void newRsaKeys() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORTHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        String publicKeyStr = Base64.encodeBase64String(publicKey.getEncoded());
        String privateKeyStr = Base64.encodeBase64String(privateKey.getEncoded());
        System.out.println("RSA::PrivateKey::" + privateKeyStr);
        System.out.println("RSA::PublicKey::" + publicKeyStr);
    }

    public static void main(String[] args) throws Exception {
        newRsaKeys();
    }

    /**
     * 解密响应
     *
     * @return
     */
    public static String decrypt(EncryptDTO dto, WeiXParamsDto weiXParamsDto) throws Exception {
        try {
            //1.验证sign
            String tempStr = "biz_data=" + dto.getBizData()
                    + "&channel_code=" + dto.getChannelCode()
                    + "&secret_key=" + dto.getSecretKey()
                    + "&timestamp=" + dto.getTimestamp();
            String sign = DigestUtils.md5Hex(tempStr + weiXParamsDto.getMd5Key());
            if (!Objects.equals(sign, dto.getSign())) {
                throw new SignException("sign验证未通过");
            }
            // 2.使用自己私钥解密secret_key 等到AES 秘钥
            String aesKey = decryptByPrivateKey(dto.getSecretKey(), weiXParamsDto.getRyhPriKey());
            // 3.使用AES秘钥解密biz_data 得到业务参数
            return aesDecrypt(dto.getBizData(), aesKey);
        } catch (Exception e) {
            throw new Exception(String.format("解密异常，message[%s]", e.getMessage()));
        }
    }

    /**
     * 加密入参
     */
    public static String encrypt(String content, WeiXParamsDto weiXParamsDto) throws Exception {
        //1.生随机字符串 作为 “AES秘钥” (注意AES加解密128/192/256 bits.对应秘钥16/24/32位)
        String key = RandomStringUtils.randomAlphanumeric(16);
        //2.将业务参数转化为Json，使用步骤1产生的秘钥进行加密 等到biz_data
        String bizData = aesEncrypt(content, key);
        //3.使用RSA公钥 加密 “AES秘钥” 得到 secret_key
        PublicKey publicKey = generateRsaPublicKey(weiXParamsDto.getWeiXPubKey());
        String secretKey = rsaEncryptWithPublicKeyToBase64(publicKey, key.getBytes(UTF_8));
        //4.将channel_code、timestamp、biz_data、secret_key 按照ASCII码升序组成字符串，格式如：biz_data=xxxxx&channel_code=xxx&secret_key=xxxx&timestamp=xxxxx
        //字符串进行MD5(tempStr + MD5Key)操作得到 sign签名
        long timestamp = TimeUnit.SECONDS.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        String tempStr = "biz_data=" + bizData + "&channel_code=" + "wxjk_ryh" + "&secret_key=" + secretKey + "&timestamp=" + timestamp;
        String sign = DigestUtils.md5Hex(tempStr + weiXParamsDto.getMd5Key());
        //5.将以上数据组成Json，post请求到接口中去，Content-type:Application/json
        JSONObject retVal = new JSONObject();
        retVal.put("biz_data", bizData);
        retVal.put("channel_code", weiXParamsDto.getChannelCode());
        retVal.put("secret_key", secretKey);
        retVal.put("timestamp", String.valueOf(timestamp));
        retVal.put("sign", sign);
        return JSON.toJSONString(retVal);
    }

    private static String aesEncrypt(String content, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, "AES"));
        byte[] bytes = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(bytes);
    }

    /**
     * @param publicKeyStr
     *
     * @return
     * @throws Exception
     */
    private static PublicKey generateRsaPublicKey(String publicKeyStr) throws Exception {
        if (publicKeyStr == null) {
            throw new NullPointerException("publicKeyStr");
        }
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("不支持的加密算法：RSA");
        }
        final byte[] publicKeyBytes = Base64.decodeBase64(publicKeyStr);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        return keyFactory.generatePublic(publicKeySpec);
    }

    private static String rsaEncryptWithPublicKeyToBase64(PublicKey publicKey, byte[] rawData) throws Exception {
        final byte[] encryptBytes = doSomethingWithKey(publicKey, rawData, Cipher.ENCRYPT_MODE);
        return Base64.encodeBase64String(encryptBytes);
    }

    private static byte[] doSomethingWithKey(Key key, byte[] rawData, int encryptMode) throws Exception {
        String algorithm = key.getAlgorithm();
        final Cipher cipher = Cipher.getInstance(algorithm.toUpperCase());
        cipher.init(encryptMode, key);
        return cipher.doFinal(rawData);
    }

    private static String decryptByPrivateKey(String value, String key) throws Exception {
        byte[] data = Base64.decodeBase64(value);
        // 对私钥解密
        byte[] keyBytes = Base64.decodeBase64(key);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(doFinalBySegment(cipher, data, false), UTF_8);
    }

    private static byte[] doFinalBySegment(Cipher cipher, byte[] source, boolean isEncode) throws Exception {
        ByteArrayOutputStream out = null;
        try {
            int blockSize = isEncode ? MAX_ENCRYPT_BLOCK : MAX_DECRYPT_BLOCK;
            if (source.length <= blockSize) {
                return cipher.doFinal(source);
            }
            out = new ByteArrayOutputStream();
            int offsetIndex = 0, offset = 0, sourceLength = source.length;
            while (sourceLength - offset > 0) {
                int size = Math.min(sourceLength - offset, blockSize);
                byte[] buffer = cipher.doFinal(source, offset, size);
                out.write(buffer, 0, buffer.length);
                offsetIndex++;
                offset = offsetIndex * blockSize;
            }
            return out.toByteArray();
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * aes解密
     *
     * @param encryptBytes 密文
     * @param key          秘钥
     *
     * @return
     * @throws Exception
     */
    private static String aesDecrypt(String encryptBytes, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, "AES"));
        byte[] decryptBytes = cipher.doFinal(Base64.decodeBase64(encryptBytes));
        return new String(decryptBytes);
    }

}