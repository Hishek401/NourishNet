package com.NourishNet.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * AESUtil - AES Encryption Utility Class
 * 
 * Uses AES/ECB/PKCS5Padding algorithm with a 16-character (128-bit) key.
 * Used to encrypt and decrypt user passwords before storing in the database.
 */
public class AESUtil {

    // AES algorithm name
    private static final String ALGORITHM = "AES";

    // Transformation: AES in ECB mode with PKCS5 padding
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    // Secret key must be exactly 16 characters (128-bit)
    private static final String SECRET_KEY = "NourishNet16Key!";

    /**
     * Encrypts a plain text string using AES encryption.
     * Returns a Base64-encoded encrypted string.
     */
    public static String encrypt(String plainText) {
        try {
            // Step 1: Create a secret key specification from the key bytes
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), ALGORITHM);

            // Step 2: Get a Cipher instance for AES/ECB/PKCS5Padding
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);

            // Step 3: Initialize cipher in ENCRYPT mode
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            // Step 4: Encrypt the plain text bytes
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));

            // Step 5: Encode encrypted bytes to Base64 string for storage
            return Base64.getEncoder().encodeToString(encryptedBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Decrypts an AES-encrypted Base64 string back to plain text.
     */
    public static String decrypt(String encryptedText) {
        try {
            // Step 1: Create a secret key specification from the key bytes
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), ALGORITHM);

            // Step 2: Get a Cipher instance for AES/ECB/PKCS5Padding
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);

            // Step 3: Initialize cipher in DECRYPT mode
            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            // Step 4: Decode Base64 and decrypt
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));

            // Step 5: Return the decrypted string
            return new String(decryptedBytes, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Main method to generate encrypted passwords for seed data.
     * Run this to get encrypted values: java com.NourishNet.util.AESUtil
     */
    public static void main(String[] args) {
        String[] passwords = {"admin123", "donor123"};
        for (String pwd : passwords) {
            String encrypted = encrypt(pwd);
            System.out.println(pwd + " => " + encrypted);
            System.out.println("Decrypted back => " + decrypt(encrypted));
            System.out.println();
        }
    }
}
