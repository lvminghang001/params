package org.params.xiaoan.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SignUtils {
    public static final String KEY = "_@Ks`Y*9jLb.hvho}C;GwDpw";
    public static final String IV = "2%8iTpSi";
    public static final String DEFAULT_ENC_NAME = "UTF-8";
    public static String encrypt(String data) {
        return encrypt(data, IV);
    }
    /**
     * java_openssl_encrypt加密算法
     *
     * @param data 待加密数据
     * @param iv 加密向量
     * @return 加密后的数据
     */
    public static String encrypt(String data, String iv) {
        try {
            Cipher cipher = null;
            try {
                cipher = createCipher(iv, Cipher.ENCRYPT_MODE);
            } catch (NoSuchPaddingException e) {
                throw new RuntimeException(e);
            } catch (InvalidAlgorithmParameterException e) {
                throw new RuntimeException(e);
            }
            return URLEncoder.encode(Base64.getEncoder()
                            .encodeToString(cipher.doFinal(data.getBytes())),
                    DEFAULT_ENC_NAME);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 解密
     *
     * @param data 待解密数据
     * @return 解密后的数据
     */
    public static String decrypt(String data) {
        return decrypt(data, IV);
    }
    /**
     * 解密
     *
     * @param data 待解密数据
     * @param iv 解密向量
     * @return 解密后的数据
     */
    public static String decrypt(String data, String iv) {
        Cipher cipher = null;
        try {
            cipher = createCipher(iv, Cipher.DECRYPT_MODE);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
        try {
            return new String(cipher.doFinal(Base64.getDecoder()
                        .decode(URLDecoder.decode(data, DEFAULT_ENC_NAME))));
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 创建密码器Cipher
     *
     * @param iv 向量
     * @param mode 加/解密模式
     * @return Cipher
     */
    private static Cipher createCipher(String iv, int mode) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException {
        byte[] key = KEY.getBytes();
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(mode, new SecretKeySpec(key, "DESede"), ivParameterSpec);
        return cipher;
    }

}