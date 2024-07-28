package org.params.common.utils;



import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;

/**
 * @author: xz
 * @since: 2024/1/11 23:03
 * @description:
 */
@Slf4j
public class AES256Util {

    /**
     * 32位长度随机密码key
     */
    private static final String PASSWORD = "MEqLCnG2Q0IfauMDbZq1lP46uP4BHsiv";

    /**
     * AES256加密
     * @param content
     * @return
     */
    public static String encrypt(String content) {
        try {
            if(content == null) {
                return "";
            }
            //根据给定的字节数组构造一个密钥。enCodeFormat：密钥内容；"AES"：与给定的密钥内容相关联的密钥算法的名称
            SecretKeySpec key = new SecretKeySpec(PASSWORD.getBytes(StandardCharsets.UTF_8), "AES");
            //将提供程序添加到下一个可用位置
            Security.addProvider(new BouncyCastleProvider());
            //创建一个实现指定转换的 Cipher对象，该转换由指定的提供程序提供。
            //"AES/ECB/PKCS7Padding"：转换的名称；"BC"：提供程序的名称
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] byteContent = content.getBytes("utf-8");
            byte[] cryptograph = cipher.doFinal(byteContent);
            return new String(Base64.encode(cryptograph));
        } catch (Exception e) {
            log.error("AES256 加密失败：", e.getMessage());
        }
        return null;
    }

    /**
     * AES256解密
     * @param cryptograph
     * @return
     */
    public static String decrypt(String cryptograph) {
        try {
            if(cryptograph == null) {
                return "";
            }
            SecretKeySpec key = new SecretKeySpec(PASSWORD.getBytes(StandardCharsets.UTF_8), "AES");
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] content = cipher.doFinal(Base64.decode(cryptograph));
            return new String(content);
        } catch (Exception e) {
            log.error("AES256 解密失败：", e.getMessage());
        }
        return null;
    }
}

