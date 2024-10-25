package org.params.guomei.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * @author: zhangfuqiang
 * @date:2023/2/9 15:34
 * @description:
 *
 * part0: 双方交换公钥，传递信息是，用对方的公钥加密传输给对方，对方用自己的私钥解密即可。生成公私钥函数{@link DemoSignUtil#genKeyPair() }
 * part1: 国美请求接口方:请求资方接口的格式包括两部分内容：http请求头和http请求体。 详细信息见接口文档3.3.1
 *     -1. randomkey、签名等都在headers 里面 见文档；
 *     -2. 真正要解密的数据在body里面；备注: 要解密数据体，首先需要解密randomkey
 *     -3. 如何加解密 参照当前类的main函数 {@link DemoSignUtil#main(String[])}
 * part2: 接口方回调国美,请求国美需要按照接口文档3.3.2 加密，国美返回信息为明文，没有加密
 */
public class DemoSignUtil {

    private static final String encoding = "UTF-8";
    private static final String AES_CBC_PKC_ALG = "AES/CBC/PKCS5Padding";
    private static final byte[] AES_IV = initIV(AES_CBC_PKC_ALG);
    private static final String RSA_ECB_PKCS1 = "RSA/ECB/PKCS1Padding";

    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";
    /**
     * 签名算法 SHA256WithRSA
     */
    public static final String SIGNATURE_ALGORITHM = "SHA256WithRSA";

    /**
     * 签名算法 SHA1WithRSA
     */
    public static final String SIGNATURE_ALGORITHM_SHA1 = "SHA1WithRSA";
    private static final Logger log = LoggerFactory.getLogger(DemoSignUtil.class);


    static class RequestParams {
        private String body;
        private Map<String, String> headers = new HashMap<>();

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }
    }

    public static void main(String[] args) {
        try {
            // 生成公私钥。公钥给国美，国美加密数据用；私钥自己保留，用于解密对方加密的数据
            Pair<String, String> pair = genKeyPair();
             String privateKey = pair.getLeft(); // 私钥
             String publicKey = pair.getRight(); // 公钥
            System.out.println("私钥:"+privateKey);
            System.out.println("公钥:"+publicKey);
        } catch (Exception e) {
            log.error("error ",e);
        }
    }

    public static String httpPost(String url, RequestParams requestParams) throws Exception {
        CloseableHttpClient client = null;
        HttpResponse resp = null;
        try {
            // 通过址默认配置创建一个httpClient实例
            client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            String respContent = null;
            httpPost.setEntity(new StringEntity(requestParams.getBody(), "utf-8"));
            Map<String, String> headers = requestParams.getHeaders();
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
            resp = client.execute(httpPost);
            if (resp == null) {
                return null;
            }
            if (resp.getStatusLine().getStatusCode() == 200) {
                log.info("请求成功，请求码={}", resp.getStatusLine().getStatusCode());
                HttpEntity he = resp.getEntity();
                respContent = EntityUtils.toString(he, "UTF-8");
            } else {
                log.error("请求失败，请求码={}", resp.getStatusLine().getStatusCode());
                return null;
            }
            return respContent;
        } catch (Exception e) {
            log.error(url + "请求失败：{}", e);
            throw e;
        } finally {
            if (null != resp) {
                try {
                    ((CloseableHttpResponse) resp).close();
                } catch (IOException e) {
                   log.error("error ",e);
                }
            }
            // 关闭资源
            if (null != client) {
                try {
                    client.close();
                } catch (IOException e) {
                    log.error("error ",e);
                }
            }
        }
    }

    /**
     * 验证签名
     *
     * @param data
     * @param publicKey
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey.getBytes(encoding));
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64.getDecoder().decode(sign.getBytes(encoding)));
    }

    /**
     * aes解密
     *
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static String decrypt4Base64(String content, String password) {
        try {
            byte[] byteContent = Base64.getDecoder().decode(content.getBytes(encoding));
            byte[] enCodeFormat = password.getBytes(encoding);
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance(AES_CBC_PKC_ALG);// 创建密码�?
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(AES_IV));// 初始�?
            byte[] result = cipher.doFinal(byteContent);
            return new String(result, encoding); // 加密
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 加密内容
     *
     * @param reqData
     * @param key
     * @param privateKey
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static RequestParams encryptReqMsg(String reqData, String key, String privateKey, String publicKey) throws Exception {
        // 请求报文按照默认顺序转json
        JSONObject reqJson = JSON.parseObject(reqData, Feature.OrderedField);

        RequestParams requestParams = new RequestParams();
        // 获取请求体
        JSONObject reqDataJson = reqJson.getJSONObject("reqData");
        JSONObject reqHeadJson = reqJson.getJSONObject("reqHead");

        //RSA签名：使用私钥对请求体业务字段进行加签
        String sign = sign(reqDataJson.toJSONString().getBytes(Charset.forName("UTF-8")), privateKey);

        requestParams.getHeaders().put("signData", sign);
        // AES加密请求数据，用16位随机密钥key明文加密请求体reqData
        String AESreqData = encrypt4Base64(reqDataJson.toString(), key);
        requestParams.setBody(AESreqData);
        // 使用公钥加密16位随机密钥key
        String randomKey = encryptByPublicKey4Pkcs5(key.getBytes(Charset.forName("UTF-8")), publicKey);
        requestParams.getHeaders().put("key", randomKey);

        Set<Map.Entry<String, Object>> entries = reqHeadJson.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            requestParams.getHeaders().put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        // 最终请求报文
        return requestParams;
    }

    /**
     * aes加密
     *
     * @param content  加密的内
     * @param password 加密密码
     * @return
     */
    public static String encrypt4Base64(String content, String password) {
        try {
            byte[] bytePwd = password.getBytes(encoding);
            SecretKeySpec key = new SecretKeySpec(bytePwd, "AES");
            Cipher cipher = Cipher.getInstance(AES_CBC_PKC_ALG);// 创建密码
            byte[] byteContent = content.getBytes(encoding);
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(AES_IV));// 初始
            byte[] result = cipher.doFinal(byteContent);
            return new String(Base64.getEncoder().encode(result)); // 加密
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return null;
    }

    /**
     * rsa解密
     *
     * @param content    加密rsaBase64
     * @param privateKey rsa私钥
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decryptByPrivateKey4Pkcs5(byte[] content, String privateKey) throws UnsupportedEncodingException {
        Cipher c = null;
        byte[] decryptedData = null;
        try {
            byte[] priByte = Base64.getDecoder().decode(privateKey.getBytes(encoding));

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(priByte);
            KeyFactory keyF = KeyFactory.getInstance("RSA");
            RSAPrivateKey pk = (RSAPrivateKey) keyF.generatePrivate(keySpec);

            c = Cipher.getInstance(RSA_ECB_PKCS1);
            c.init(Cipher.DECRYPT_MODE, pk);

            byte[] contentB = Base64.getDecoder().decode(new String(content, encoding).getBytes(encoding));
            int inputLen = contentB.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > 128) {
                    cache = c.doFinal(contentB, offSet, 128);
                } else {
                    cache = c.doFinal(contentB, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * 128;
            }
            decryptedData = out.toByteArray();
            out.close();

        } catch (Exception e) {
            return null;
        }
        return new String(decryptedData, encoding);
    }

    /**
     * 公钥加密
     *
     * @param data      源数
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey4Pkcs5(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKey.getBytes(encoding));
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey publicK = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        // 对数据加�?
        Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS1);
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > 128) {
                cache = cipher.doFinal(data, offSet, 128);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * 128;
        }
        byte[] encryptedData = out.toByteArray();

        out.close();
        return new String(Base64.getEncoder().encode(encryptedData));
    }

    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKey.getBytes(encoding));
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return new String(Base64.getEncoder().encode(signature.sign()));
    }

    /**
     * 随机数
     *
     * @return
     */
    public static String getRandom() {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            sb.append("1234567890qwertyuiopasdfghjklzxcvbnm".charAt(random
                    .nextInt("1234567890qwertyuiopasdfghjklzxcvbnm".length())));
        }
        return sb.toString();
    }

    public static Pair<String, String> genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        // 得到公钥字符串
        String publicKeyString = new String(Base64.getEncoder().encode(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.getEncoder().encode((privateKey.getEncoded())));

        return Pair.of(privateKeyString, publicKeyString);
    }

    /**
     * 初始化向
     *
     * @param aesCbcPkcAlg
     * @return
     */
    private static byte[] initIV(String aesCbcPkcAlg) {
        Cipher cp;
        try {
            cp = Cipher.getInstance(aesCbcPkcAlg);
            int blockSize = cp.getBlockSize();
            byte[] iv = new byte[blockSize];
            for (int i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }
            return iv;

        } catch (Exception e) {
            int blockSize = 16;
            byte[] iv = new byte[blockSize];
            for (int i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }
            return iv;
        }
    }
}
