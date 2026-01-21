package org.params.bjxd;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wangshuilin
 * @Since: 2024/12/9
 * @Version: 1.0
 * @Description: 八戒小贷工具类
 */
@Slf4j
public class BaJieXiaoDaiUtil {



    /**
     * 加密
     *
     * @param content 消息内容
     * @param key     密钥
     * @return 密文
     * @throws Exception 抛异常
     */
    public static String encrypt(String content, String key){
        try {
            if (key == null || key.length() != 16 || content == null) {
                return null;
            }
            byte[] aesKey = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(aesKey, "AES");
            // 使用CBC 模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(content.getBytes(BaJieXiaoDaiConstants.CHARSET));
            // 此处使用BASE64 做转码。
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            log.error("八戒小贷-加密失败:{}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 解密
     *
     * @param content 密文
     * @param key     密钥
     * @return 消息内容
     * @throws Exception 抛异常
     */
    public static String decrypt(String content, String key){
        try {
            if (key == null || key.length() != 16 || content == null) {
                return null;
            }
            content = formatContent(content);
            byte[] aesKey = key.getBytes(BaJieXiaoDaiConstants.CHARSET);
            SecretKeySpec skeySpec = new SecretKeySpec(aesKey, "AES");
            // 使用CBC 模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            // 先用base64 解密
            byte[] encrypted1 = Base64.getDecoder().decode(content);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, BaJieXiaoDaiConstants.CHARSET);
        } catch (Exception e) {
            log.error("八戒小贷-解密失败:{}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 格式化加密的消息体去除换行符
     *
     * @param content 加密的消息体
     * @return 格式化后加密的消息体
     */
    public static String formatContent(String content) {
        String s = content;
        // 1.去掉引号
        if (content.contains("\"")) {
            s = s.substring(1, s.length() - 1);
        }
        // 2.替换\r 已经被转义的字符
        if (content.contains("\\r")) {
            s = s.replace("\\r", "\r");
        }
        // 3.替换\n 已经被转义的字符
        if (content.contains("\\n")) {
            s = s.replace("\\n", "\n");
        }
        // 4.替换空格
        s = s.replace(" ", "");
        return s;
    }

    /**
     * 用SHA1 算法生成安全签名
     *
     * @param token     票据
     * @param timestamp 时间戳
     * @param nonce     随机字符串
     * @param encrypt   密文
     * @return 安全签名
     * @throws Exception
     */
    public static String getSHA1(String token, String timestamp, String nonce, String encrypt){
        try {
            String[] array = new String[]{token, timestamp, nonce, encrypt};
            StringBuilder sb = new StringBuilder();
            // 字符串排序
            Arrays.sort(array);
            for (int i = 0; i < 4; i++) {
                sb.append(array[i]);
            }
            String str = sb.toString();
            // SHA1 签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();
            StringBuilder hexstr = new StringBuilder();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (Exception e) {
            log.error("八戒小贷-获取签名失败:{}", e.getMessage(), e);
            return null;
        }
    }

}
