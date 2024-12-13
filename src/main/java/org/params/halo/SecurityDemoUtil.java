package org.params.halo;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SecurityDemoUtil {
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;
    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding"
            ;
    private static final Random random = new Random();
    /**
     * RSA⾮对称加密
     * @param content 待加密明⽂
     * @param publicKey 对⽅公钥
     * @param inputCharset 编码
     * @return
     * @throws Exception
     */
    public static String encryptRSA(String content, String publicKey, String inputCharset) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] encodedKey = Base64.decodeBase64(publicKey);
        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, pubKey);
        InputStream ins = new ByteArrayInputStream(content.getBytes(inputCharset));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        int bufl;
        byte[] block;
        for(byte[] buf = new byte[117]; (bufl = ins.read(buf)) != -1;
            writer.write(cipher.doFinal(block))) {
            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                System.arraycopy(buf, 0, block, 0, bufl);
            }
        }
        return new String(Base64.encodeBase64(writer.toByteArray()), inputCharset);
    }
    /**
     * RSA⾮对称解密
     * @param content 待解密密⽂
     * @param privateKey ⼰⽅私钥
     * @param inputCharset 编码
     * @return
     * @throws Exception
     */
    public static String decryptRSA(String content, String privateKey, String inputCharset) throws Exception {
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        KeyFactory keyf = KeyFactory.getInstance("RSA");
        PrivateKey priKey = keyf.generatePrivate(priPKCS8);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, priKey);
        InputStream ins = new ByteArrayInputStream(Base64.decodeBase64(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        int bufl;
        byte[] block = null;
        for(byte[] buf = new byte[128]; (bufl = ins.read(buf)) != -1;
            writer.write(cipher.doFinal(block))){
            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                System.arraycopy(buf, 0, block, 0, bufl);
            }
        }
        return new String(writer.toByteArray(), inputCharset);
    }
    /**
     * AES加密
     * @param data 待加密明⽂
     * @param key 密钥
     * @param inputCharset 编码
     * @return
     * @throws Exception
     */
    public static String encryptAES(String data, String key, String inputCharset) throws Exception {
        Key k = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(1, k);
        return Base64.encodeBase64String(cipher.doFinal(data.getBytes(inputCharset)));
    }
    /**
     * AES解密
     * @param data 待解密密⽂
     * @param key 密钥
     * @param inputCharset 编码
     * @return
     * @throws Exception
     */
    public static String decryptAES(String data, String key, String inputCharset) throws Exception {
        Key k = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(2, k);
        return new String(cipher.doFinal(Base64.decodeBase64(data)), inputCharset);
    }
    /**
     * MD5加密
     * @param value 待加密字符串
     * @return
     */
    public static String genMD5(String value) {
        return DigestUtils.md5Hex(value).toUpperCase();
    }
    /**
     * 获取随机密钥明⽂
     * @return
     */
    public static String getAESRandomKey() {
        long longValue = random.nextLong();
        return String.format("%016x", longValue);
    }
    /**
     * 加解密使⽤示例
     * @param args
     */
    public static void main(String[] args) {
        try {
            //对⽅公钥，测试都使⽤同⼀套
            String thirdPubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD"+
            "M1MvJw/yU6OFfvSEuU0h9eg9Y9Zm8RnSgzV7Hgj/qU+IM8CeLI/kRxVmyHL4fyUQO5qRyhL80"+
            "yRyFUKKjUlLhwbzbtKg3XLsFv+lhuA0C5/NPYmrS8e/5LTLS0zNWDdMpJZEx9Y5TIPDnqxrDR"+
            "sV2lNZd82CHzAd7aMe5fBatwQIDAQAB";
            //⼰⽅私钥，测试都使⽤同⼀套
            String priKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEA"+
            "AoGBAMzUy8nD/JTo4V+9IS5TSH16D1j1mbxGdKDNXseCP+pT4gzwJ4sj+RHFWbIcvh/JRA7mp"+
            "HKEvzTJHIVQoqNSUuHBvNu0qDdcuwW/6WG4DQLn809iatLx7/ktMtLTM1YN0yklkTH1jlMg8O"+
            "erGsNGxXaU1l3zYIfMB3tox7l8Fq3BAgMBAAECgYBxV/tgrcPR/r/fw39d7BX74RQnDNjCV1Z"+
            "oONyOl+OYXkyDDk1DcGd9zu/gYIlQe4XenQA4on5Pzk2q88DbkU1swwoFhkvWFAOqF6UEYujh"+
            "ymY86MrobNXqztqO4RcF3OH5IrDfRGXtEr+fGNPwyu+IyjhNXxW5BdvobjmgGVVS9QJBAPiE8"+
            "J/JvQxUmmucyGmPNNhnFMvBJiXjIq80ce9Yn+kFjJmP0VkTP0Y5b2uTdqVBdEghZESVPFzw5L"+
            "PbAqOP1QMCQQDS/zN8Jh5R/0xodZkhcfKanN9Fa5wDIVjiIAY18iQiDR9ekMqUzl14LiDGa+f"+
            "MhlVYXjUIiyBR0k2zOReJBgzrAkA+9J5oPBjR+NStkigK5aZDc8mG3EUnr+Rnceey9EZ+J1O4"+
            "ywADiqaqyX36SH7z2iL06tCVtyB1gujMzxxaBuO1AkBZlF+XZdeZmHooH0VUHbySR+fC4VzrN"+
            "001M8NvQ85zZn7a9z4Kz1J/o5XmqAlRm/a//b8mUWr3UgILBUIoupjhAkAh8gPg8eqpgTW1AV"+
            "zAcSwREDyg2H2fz+AkKa+rb1eSdbAvqC4CObJVjw5m/QC/wumbwYPgeFXmCMmC+3cWw2Dh";
            Map<String,String> map = new HashMap<>();
            map.put("mobileNo","5158062ff8a7ed200d8a1a47dbf199af");
            //明⽂A
            String A = JSON.toJSONString(map);
            System.out.println("明⽂A====:"+A);
            /******************以下为加密加签流程*************************/
            //获取随机密钥R
            String R =getAESRandomKey();
            System.out.println("随机密钥明⽂R:"+R);
            //使⽤随机密钥R对明⽂A进⾏AES对称加密,得到密⽂M
            String M =encryptAES(A, R, "utf-8");
            System.out.println("请求密⽂M:"+M);
            String K = encryptRSA(R, thirdPubKey, "utf-8");
            System.out.println("随机密钥密⽂K:"+K);
            String B = genMD5(A);
            System.out.println("请求明⽂MD5加密B:"+B);
            //加签
            String S = encryptRSA(B,thirdPubKey, "utf-8");
            System.out.println("签名S:"+S);
            /*
             * 以下打印为可直接拼接到URL上的参数
             * 如果是POST请求，参数为如下JSON:
             * {
             * "ciphertext":M,
             * "signData":S,
             * "randomKey":K
            */

            System.out.println("ciphertext="+M+"&signData="+S+"&randomKey="+K);
                    /********************以下为解密验签流程*************************/
                    //解密
                    //对随机密钥密⽂K进⾏解密
                    String R1 =decryptRSA(K, priKey, "utf-8");
            System.out.println("解密后随机密钥R："+R1);
            //⽤解密得到的随机密钥明⽂对密⽂M进⾏解密
            String A1 =decryptAES(M, R1, "utf-8");
            System.out.println("随机密钥R1："+R1+"，解密明⽂："+A1);
            //验签
            //对解密出来的A1明⽂进⾏MD5处理得到摘要C
            String C =genMD5(A1);
            System.out.println("对明⽂A1进⾏MD5加密得到摘要C："+C);
            //⽤⼰⽅私钥对签名S进⾏解密得到摘要B1
            String B1 =decryptRSA(S, priKey, "utf-8");
            System.out.println("对签名S进⾏解密得到摘要B1："+B1);
            //判断C和B1是否⼀致，⼀致则通过验签
            Boolean passResult = C.equals(B1) ?Boolean.TRUE:Boolean.FALSE;
            System.out.println("验签通过结果:" + passResult);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
