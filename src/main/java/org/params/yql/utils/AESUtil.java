package org.params.yql.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Slf4j
public class AESUtil {

    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            log.error("AES加密失败！--密钥为null");
            return null;
        }
// 判断Key是否为16位
        if (sKey.length() != 16) {
            log.error("AES加密失败！--密钥只能为16位字符串");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        return new Base64().encodeToString(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。

    }

// 解密

    public static String decrypt(String sSrc, String sKey) {
        try {
            if (sKey == null) {
                log.error("AES解密失败！--密钥为null");
                return null;
            }
            if (sKey.length() != 16) {
                log.error("AES解密失败！--密钥只能为16位字符串");
                return null;
            }
            byte[] raw = sKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(sSrc);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                return new String(original, StandardCharsets.UTF_8);
            } catch (Exception e) {
                log.error("解密出现错误，错误原因【{}】",e.getMessage());
                e.printStackTrace();
                return null;
            }

        } catch (Exception ex) {
            log.error("AES解密失败！", ex);
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        /*

         * 此处使用AES-128-ECB加密模式，key需要为16位。

         */

// String cKey = "1234567890123456";

        String cKey = "kzmor2r4ccac54i6";

// 需要加密的字串

        String cSrc = "fdf1f625ae570ddacc6a9d8b17530e03";

        System.out.println(cSrc);

// 加密

        String enString = Encrypt(cSrc, cKey);

        System.out.println("加密后的字串是：" + enString);

// 解密

        String DeString = decrypt(enString, cKey);

        System.out.println("解密后的字串是：" + DeString);

    }

}
