package org.params.bigData.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author LiYu
 * @ClassName BusinessPushUtil.java
 * @Description 业务推送工具类
 * @createTime 2024年07月16日 18:25:00
 */
@Slf4j
public class BusinessPushUtil {
    private static final String KEY = "]+atg}`Ff*bcH[99M#-G%rI,s-n;nMR=";

    /**
     * 数据加密
     *
     * @param data 数据
     * @param key  密钥
     * @return 加密后的数据
     */
    public static String encrypt(String data, String key) {
        return SecureUtils.AesUtil.encrypt(data, key);
    }

    /**
     * 数据加密
     *
     * @param data 数据
     * @return 加密后的数据
     */
    public static String encrypt(String data) {
        return SecureUtils.AesUtil.encrypt(data, KEY);
    }


    /**
     * 数据解密
     *
     * @param data 数据
     * @param key  密钥
     * @return 解密后的数据
     */
    public static String decrypt(String data, String key) {
        return SecureUtils.AesUtil.decrypt(data, key);
    }


    /**
     * 数据解密
     *
     * @param data 数据
     * @return 解密后的数据
     */
    public static String decrypt(String data) {
        try {
            return SecureUtils.AesUtil.decrypt(data, KEY);
        } catch (Exception e) {
            log.error("解密失败", e);
            throw new RuntimeException("解密失败！");
        }
    }

    public static void main(String[] args) {
        String a = "https://koetwpbc.dysddfq.com/system/user";
        System.out.println(encrypt(a, KEY));
    }

}
