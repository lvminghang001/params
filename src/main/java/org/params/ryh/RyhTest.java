package org.params.ryh;

import org.params.common.dto.EncryptUtil;

import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//32位，小写

public class RyhTest {



    public final static String devEnCode="i5t9zs843tpPYsXgP0ptE0z73HHLTdKMHdbUcxGYCyWQG0YhzvyM7nL5xuJz27im";

    public final static String uatEnCode="i5t9zs843tpPYsXgP0ptE0z73HHLTdKMHdbUcxGYCyWQG0YhzvyM7nL5xuJz27im";
    public static void main(String[] args) {
        String phone="IWNAnUIpkqtuSNWQyuWh6A==";   //15812341284
        System.out.println(EncryptUtil.AESdecode(phone, uatEnCode));
//
//        String name="ILHr8lectmO0GU8OeMOXiA==";
//        System.out.println(EncryptUtil.AESdecode(name, uatEnCode));
//
//        String nam2="15398760467";
//        System.out.println(EncryptUtil.AESencode(nam2, uatEnCode));

//
//        String md532Lower = DigestUtils.md5DigestAsHex("19099113525".getBytes());
//        System.out.println(md532Lower);
//        //32位，大写
//        String md532Upper=md532Lower.toUpperCase();
//        System.out.println(md532Upper);
//        //16位，小写
//        String md516Lower =md532Lower.substring(8, 24);
//        System.out.println(md516Lower);
//        //16位，大写
//        String md516Upper=md532Lower.substring(8, 24).toUpperCase();
//        System.out.println(md516Upper);

        String input = "25A167D3558C68110BFDE88B46E2784A";
        System.out.println(convertToLowerCase(input));
        String md5Hex = calculateMD5(input);
        System.out.println("MD5 Hash in hex: " + md5Hex);
        String str="25A167D3558C68110BFDE88B46E2784A";
        System.out.println(str.matches("^[a-f0-9]{32}$") || str.matches("^[A-F0-9]{32}$"));
        System.out.println(str.matches("^[A-F0-9]{32}$"));
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
