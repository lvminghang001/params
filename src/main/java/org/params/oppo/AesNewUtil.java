package org.params.oppo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author XuYu
 * @program channel-demo->AesNewUtil
 * @description
 * @create 2021-11-02 16:06
 **/
public class AesNewUtil {

    private static final Logger logger = LoggerFactory.getLogger(AesNewUtil.class);

    // 算法定义
    private static final String AES_ALGORITHM = "AES";
    // 指定填充方式
    private static final String AES_PADDING = "AES/CBC/PKCS5Padding";

    /***
     * AES加密
     *
     * @param content 待加密内容
     * @param aesKey 密钥
     * @return
     */
    public static String encrypt(String content, String aesKey) {
        // 默认使用U8编码
        return encrypt(content, aesKey, "utf-8");
    }

    /***
     * AES加密
     *
     * @param content 待加密内容
     * @param aesKey 密钥
     * @param encoding 编码
     * @return
     */
    public static String encrypt(String content, String aesKey, String encoding) {
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(aesKey.getBytes());
            SecretKeySpec key = new SecretKeySpec(aesKey.getBytes(encoding), AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_PADDING);// 创建密码器
            byte[] byteContent = content.getBytes(encoding);
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);// 初始化
            // 加密
            byte[] result = cipher.doFinal(byteContent);
            // 转换16进制字符串后返回
            return ByteUtil.bytesToHexString(result);
        } catch (NoSuchAlgorithmException e) {
            logger.error("aes encrypt NoSuchAlgorithmException error.", e);
        } catch (NoSuchPaddingException e) {
            logger.error("aes encrypt NoSuchPaddingException error.", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("aes encrypt UnsupportedEncodingException error.", e);
        } catch (InvalidKeyException e) {
            logger.error("aes encrypt InvalidKeyException error.", e);
        } catch (IllegalBlockSizeException e) {
            logger.error("aes encrypt IllegalBlockSizeException error.", e);
        } catch (BadPaddingException e) {
            logger.error("aes encrypt BadPaddingException error.", e);
        } catch (InvalidAlgorithmParameterException e) {
            logger.error("aes encrypt InvalidAlgorithmParameterException error.", e);
        }

        return null;
    }

    /***
     * AES解密，输入密文16进制字符串,默认使用U8编码
     *
     * @param cipherText
     * @param aesKey
     * @return
     */
    public static String decrypt(String cipherText, String aesKey) {
        // 将16进制的密文转换为byte类型
        byte[] cipherTextByte = ByteUtil.hexStringToBytes(cipherText);
        if (cipherTextByte == null) {
            return null;
        }
        // 默认使用U8编码返回原字符串
        return decrypt(cipherTextByte, aesKey, "utf-8");
    }

//    public static void main(String[] args) {
////        decrypt("5DA4392193A872631C0163B403D0E11692F44E90D708930404847F5FC52D137D", "hzmknly7u7x58nsu");
//        decrypt("291FFF49F082A13C06653D6164BA0720692BB48FAA57FA7AC7845732D6601D89", "pjeyu6euads");
//    }

    /***
     * AES解密，输入为byte数组,默认使用U8编码
     *
     * @param cipherTextByte 加密内容
     * @param aesKey 密钥
     * @return
     */
    public static String decrypt(byte[] cipherTextByte, String aesKey) {
        // 默认使用U8编码返回原字符串
        return decrypt(cipherTextByte, aesKey, "utf-8");
    }

    /***
     * AES解密，输入为byte数组,可以指定编码
     *
     * @param cipherTextByte 加密内容
     * @param aesKey 密钥
     * @param encoding 编码
     * @return
     */
    public static String decrypt(byte[] cipherTextByte, String aesKey, String encoding) {
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(aesKey.getBytes("UTF-8"));
            SecretKeySpec key = new SecretKeySpec(aesKey.getBytes(encoding), AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_PADDING);// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);// 初始化
            byte[] result = cipher.doFinal(cipherTextByte);
            return new String(result, encoding);
        } catch (NoSuchAlgorithmException e) {
            logger.error("aes decrypt NoSuchAlgorithmException error.", e);
        } catch (NoSuchPaddingException e) {
            logger.error("aes decrypt NoSuchPaddingException error.", e);
        } catch (InvalidKeyException e) {
            logger.error("aes decrypt InvalidKeyException error.", e);
        } catch (IllegalBlockSizeException e) {
            logger.error("aes decrypt IllegalBlockSizeException error.", e);
        } catch (BadPaddingException e) {
            logger.error("aes decrypt BadPaddingException error.", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("aes decrypt UnsupportedEncodingException error.", e);
        } catch (InvalidAlgorithmParameterException e) {
            logger.error("aes decrypt InvalidAlgorithmParameterException error.", e);
        }

        return null;
    }

}

