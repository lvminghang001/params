package org.params.shanqian;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

public class CreditApiAesUtils {

    /**
     * 加密模式之 CBC，算法/模式/补码方式
     */
    private static final String AES_CBC = "AES/CBC/PKCS5Padding";
    private static final String key="okijuhyjikijuhyg";//测试密钥
    private static final String iv="ijuhgtdfyguhijok";//随机16位英文数字 字符串
    /**
     * 加密 - 自定义加密模式
     *
     * @param text 需要加密的文本内容
     * @param key 加密的密钥 key
     * @param iv 初始化向量 16位
     */
    public static String encrypt(String text, String key, String iv) {
        if (isEmpty(text) || isEmpty(key) || isEmpty(iv)) {
            return null;
        }
        try {
// 创建AES加密器
            Cipher cipher = Cipher.getInstance(AES_CBC);
            SecretKeySpec secretKeySpec = new
                    SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new
                    IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8)));
// 加密字节数组
            byte[] encryptedBytes = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
// 将密文转换为 Base64 编码字符串
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decryptData(String text){
        return decrypt(text,key,iv);
    }

    /**
     * 解密 - 自定义加密模式
     *
     * @param text 需要解密的文本内容
     * @param key 解密的密钥 key
     * @param iv 初始化向量 16位
     */
    public static String decrypt(String text, String key, String iv) {
        if (isEmpty(text) || isEmpty(key) || isEmpty(iv)) {
            return null;
        }
// 将密文转换为16字节的字节数组
        byte[] textBytes = Base64.getDecoder().decode(text);
        try {
// 创建AES加密器
            Cipher cipher = Cipher.getInstance(AES_CBC);
            SecretKeySpec secretKeySpec = new
                    SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new
                    IvParameterSpec(Objects.requireNonNull(iv.getBytes(StandardCharsets.UTF_8))));
// 解密字节数组
            byte[] decryptedBytes = cipher.doFinal(textBytes);
// 将明文转换为字符串
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /***
     * 空校验
     * @param str 需要判断的值
     */
    public static boolean isEmpty(Object str) {
        return null == str || "".equals(str);
    }

}
