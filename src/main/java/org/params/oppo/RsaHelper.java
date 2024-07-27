package org.params.oppo;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author XuYu
 * @program channel-demo->RsaHelper
 * @description
 * @create 2021-11-02 16:07
 **/
public class RsaHelper {

    private static final Logger LOG = LoggerFactory.getLogger(RsaHelper.class);

    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";
    public static final String CHIPER_ALGORITHM = "RSA/None/PKCS1Padding";
    public static final String CHIPER_ALGORITHM2 = "RSA/ECB/PKCS1Padding";
    private static BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();

    /**
     * RSA公钥加密
     *
     * @param data         待加密字符串
     * @param publicKeyStr 私钥
     * @return
     */
    public static String encryptByPublicKey(String data, String publicKeyStr) {
        try {
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, bouncyCastleProvider);
            PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance(CHIPER_ALGORITHM, bouncyCastleProvider);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] resultBytes = cipher.doFinal(data.getBytes("UTF-8"));
            return Base64.encodeBase64String(resultBytes);
        } catch (Exception e) {
            LOG.error("encript err", e);
            throw new RuntimeException(e.toString());
        }
    }

    /**
     * RSA公钥加密
     *
     * @param data          待加密字符串
     * @param privateKeyStr 私钥
     * @return
     */
    public static String encryptByPrivateKey(String data, String privateKeyStr) {
        try {
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, bouncyCastleProvider);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(CHIPER_ALGORITHM, bouncyCastleProvider);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] resultBytes = cipher.doFinal(data.getBytes("UTF-8"));
            return Base64.encodeBase64String(resultBytes);
        } catch (Exception e) {
            LOG.error("encript err", e);
            throw new RuntimeException(e.toString());
        }
    }

    /**
     * RSA私钥解密
     *
     * @param encryptedData 待签名字符串
     * @param privateKeyStr 私钥
     * @return
     */
    public static String decryptByPrivateKey(String encryptedData, String privateKeyStr) {
        try {
            byte bytes[] = Base64.decodeBase64(encryptedData);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, bouncyCastleProvider);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(CHIPER_ALGORITHM, bouncyCastleProvider);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] resultBytes = cipher.doFinal(bytes);
            return new String(resultBytes, "UTF-8");
        } catch (Exception e) {
            LOG.error("Failed to decrypt the field string {}: {}", encryptedData, e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    public static String decryptByPublicKey(String encryptedData, String publicKeyStr) {
        try {
            byte bytes[] = Base64.decodeBase64(encryptedData);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, bouncyCastleProvider);
            PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance(CHIPER_ALGORITHM, bouncyCastleProvider);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] resultBytes = cipher.doFinal(bytes);
            return new String(resultBytes, "UTF-8");
        } catch (Exception e) {
            LOG.error("Failed to decrypt the field string {}: {}", encryptedData, e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    public static String decryptByPrivateKeyToHex(String encryptedData, String privateKeyStr) {
        try {
            byte bytes[] = Base64.decodeBase64(encryptedData);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, bouncyCastleProvider);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(CHIPER_ALGORITHM, bouncyCastleProvider);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] resultBytes = cipher.doFinal(bytes);
//            return new String(resultBytes, "UTF-8");
            return bytesToHex(resultBytes);
        } catch (Exception e) {
            LOG.error("Failed to decrypt the field string {}: {}", encryptedData, e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    public static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() < 2) {
            hex = "0" + hex;
        }
        return hex;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }


    /**
     * RSA私钥解密
     * 发生异常返回原文
     *
     * @param encryptedData 待签名字符串
     * @param privateKeyStr 私钥
     * @return
     */
    public static String decryptByPrivateKeyCatch(String encryptedData, String privateKeyStr) {
        if (StringUtils.isBlank(encryptedData)) {
            return encryptedData;
        }

        try {
            byte bytes[] = Base64.decodeBase64(encryptedData);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM, bouncyCastleProvider);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(CHIPER_ALGORITHM, bouncyCastleProvider);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] resultBytes = cipher.doFinal(bytes);
            return new String(resultBytes, "UTF-8");
        } catch (Exception e) {
            LOG.error("Failed to decrypt the field string {}: {}", encryptedData, e.getStackTrace());
            return encryptedData;
        }
    }

    private RsaHelper() {
    }


}

