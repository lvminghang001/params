package org.params.yql.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.binary.Base64;


/**
 * @description AES加密/解密
 */
@Slf4j
public class AesUtils {

    public static final String AES = "AES";
    public static final String AES_CBC_CIPHER = "AES/CBC/PKCS5Padding";

    /**
     * AES 加密 CBC模式
     *
     * @param data 需要加密的内容
     * @param key  密钥
     * @return 密文
     */
    public static String encrypt(String data, String key) {
        byte[] dataByteArray = data.getBytes(StandardCharsets.UTF_8);
        byte[] keyByteArray = new Base64().decode(key);
        try {
            SecretKeySpec secretKey = new SecretKeySpec(keyByteArray, AES);
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, AES);
            Cipher cipher = Cipher.getInstance(AES_CBC_CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(keyByteArray));
            byte[] valueByte = cipher.doFinal(dataByteArray);
            return new Base64().encodeToString(valueByte);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * AES 解密 CBC模式
     *
     * @param data 待解密内容 base64 字符串
     * @param key  密钥
     * @return 明文
     */
    public static String decrypt(String data, String key) {
        byte[] originalData = new Base64().decode(data.getBytes());
        byte[] keyByteArray = new Base64().decode(key);
        try {
            SecretKeySpec secretKey = new SecretKeySpec(keyByteArray, AES);
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, AES);
            Cipher cipher = Cipher.getInstance(AES_CBC_CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(keyByteArray));
            byte[] valueByte = cipher.doFinal(originalData);
            return new String(valueByte,StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}