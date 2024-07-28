package org.params.common.utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

public class AESKey {

    public static void main(String[] args) {
        try {
            // Generate AES key
            SecretKey secretKey = generateAESKey();

            // Print the generated key (in bytes)
            byte[] keyBytes = secretKey.getEncoded();
            System.out.println("Generated AES key (in bytes): " + bytesToHex(keyBytes));

            // Optionally, you can convert it to a Base64 string
            String base64Key = java.util.Base64.getEncoder().encodeToString(keyBytes);
            System.out.println("Generated AES key (Base64 encoded): " + base64Key);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static SecretKey generateAESKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // 128-bit AES key
        return keyGen.generateKey();
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }
}

