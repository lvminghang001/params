package org.params.ryh;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.params.common.dto.EncryptUtil;

import org.params.common.utils.RyhRsaUtil;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//32位，小写

public class RyhTest {



    public final static String devEnCode="i5t9zs843tpPYsXgP0ptE0z73HHLTdKMHdbUcxGYCyWQG0YhzvyM7nL5xuJz27im";

    public final static String uatEnCode="i5t9zs843tpPYsXgP0ptE0z73HHLTdKMHdbUcxGYCyWQG0YhzvyM7nL5xuJz27im";
    public static void main(String[] args) {
//
//        String text="iPhone XS Max";
//        System.out.println(text.contains("iphone"));
//        String phone="17100000090";
//        System.out.println(EncryptUtil.AESencode(phone, uatEnCode));

        String workAdcode ="469022";
        String workProvince = workAdcode.substring(0,2)+"0000";//根据区编码拼接省编码
        String workCity = workAdcode.substring(0,4)+"00";//根据区编码拼接市编码
        System.out.println(workProvince+" "+workCity);
    }

    public static String calculateMD5(String input) {
        try {
            // 获取 MD5 消息摘要实例
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 计算输入数据的散列值
            byte[] mdBytes = md.digest(input.getBytes());

            // 将字节数组转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : mdBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }



    public static String convertToLowerCase(String md5UpperCase) {
        return md5UpperCase.toLowerCase();
    }



}
