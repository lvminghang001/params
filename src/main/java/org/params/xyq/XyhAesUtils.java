package org.params.xyq;

import cn.hutool.core.util.RandomUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class XyhAesUtils {
    /**
     * 015 ： AES-128-ECB（加密算法）
     * key = "oxAvmnw6cqcsdRLT";
     */
    public static String encrypt(String sSrc, String sKey) {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
// 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw;
        try {
            raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            return new org.apache.commons.codec.binary.Base64().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * AES-ECB算法128位解密
     *
     * @param encrypMsg
     * @return
     */
    public static String decrypt(String encrypMsg,String aesKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] encrypted1 = Base64.decode(encrypMsg);
            byte[] decbbdt = cipher.doFinal(encrypted1);
            return new String(decbbdt, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        String key = RandomUtil.randomString(16);
        System.out.println(key);
        String body = "{\"md5Phone\":\"13566666666\"}";
        String encrypt = encrypt(body, key);
        System.out.println("加密===》" + encrypt);
        String decrypt = decrypt(encrypt, key);
        System.out.println("解密===》" + decrypt);
    }
}
