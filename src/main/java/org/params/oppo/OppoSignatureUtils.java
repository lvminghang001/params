package org.params.oppo;


import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author XuYu
 * @program channel-demo->SignatureUtils
 * @description
 * @create 2021-11-02 16:08
 **/
public class OppoSignatureUtils {

    /**
     * 根据字符串获取公钥
     *
     * @param key
     * @return PublicKey
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = new Base64().decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 根据字符串获取私钥
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = new Base64().decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * @param privateKey
     * @param str
     * @throws InvalidKeyException
     * @throws Exception
     */
    public static String sign(String privateKey, String str) throws InvalidKeyException, Exception {
        // SHA1withRSA算法进行签名
        Signature sign = Signature.getInstance("SHA1WithRSA");
        sign.initSign(getPrivateKey(privateKey));
        sign.update(str.getBytes(StandardCharsets.UTF_8));
        return new String(new Base64().encode(sign.sign()));
    }

    public static boolean verify(String publicKey, String data, String sign) throws InvalidKeyException, Exception {
        // 用于验签的数据
        byte[] bytesign = new Base64().decode(sign);
        Signature verifySign = Signature.getInstance("SHA1WithRSA");
        verifySign.initVerify(getPublicKey(publicKey));
        verifySign.update(data.getBytes(StandardCharsets.UTF_8));
        return verifySign.verify(bytesign);
    }

}