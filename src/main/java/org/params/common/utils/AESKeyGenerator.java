package org.params.common.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AESKeyGenerator {

    private SecretKey secretKey;
    private Cipher cipher;

    public AESKeyGenerator() throws NoSuchAlgorithmException {
        // Generate AES key
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // AES key size can be 128, 192, or 256 bits
        this.secretKey = keyGen.generateKey();

        // Create AES cipher
        String base64Key = java.util.Base64.getEncoder().encodeToString(this.secretKey.getEncoded());
        System.out.println("Generated AES key (Base64 encoded): " + base64Key);
        try {
            this.cipher = Cipher.getInstance("AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  SecretKey getSecretKey() {
        return secretKey;
    }

    public String encrypt(String plainText) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedText) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    public static void main(String[] args) throws Exception {
        AESKeyGenerator aesKeyGen = new AESKeyGenerator();

        // Example usage: encrypt and decrypt a message
        String originalMessage = "Hello, AES!";

        String encryptedMessage = aesKeyGen.encrypt(originalMessage);
        String decryptedMessage = aesKeyGen.decrypt(encryptedMessage);

        System.out.println("Original message: " + originalMessage);
        System.out.println("Encrypted message: " + encryptedMessage);
        System.out.println("Decrypted message: " + decryptedMessage);
    }
}

