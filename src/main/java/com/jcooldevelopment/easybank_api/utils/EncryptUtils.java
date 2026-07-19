package com.jcooldevelopment.easybank_api.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jcooldevelopment.easybank_api.exception.EncryptionException;

public class EncryptUtils {

    // https://www.baeldung.com/java-secure-random
    /**
     * Generates a random string made of chars and int.
     * @param n The length of the generated string
     * @return The generated user code
     */
    private static String generateRandomString(int n) {
        // Possible 64 chars for random string
        String chars = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder("");
        SecureRandom intGenerator = new SecureRandom();

        // For each of randomString, we get each char
        for(int i = 0; i<n; i++) {
            var randomNumber = intGenerator.nextInt(0,chars.length());
            randomString.insert(i, chars.charAt(randomNumber));
        }

        // Transform StringBuilder to String
        return randomString.toString();
    }

    /**
     * Generates an user code for login.
     * @return The generated user code
     */
    public static String generateUsercode() {
        return generateRandomString(10);
    }

    // https://javarevisited.blogspot.com/2013/03/generate-md5-hash-in-java-string-byte-array-example-tutorial.html
    /**
     * Generates SHA-256 hash.
     * @param value The value to hash.
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @return The SHA-256 hash.
     */
    public static String shaHash(String value) {
        String digest = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // SHA-256 generates always the same hash with the same original value
            byte[] hash = md.digest(value.getBytes("UTF-8"));

            // Convert bytes to hexadecimal
            StringBuilder sb = new StringBuilder(2*hash.length);
            for(byte b : hash){
                sb.append(String.format("%02x", b&0xff));
            }
          
            digest = sb.toString();

        } catch (UnsupportedEncodingException exception) {
            Logger.getLogger("Unsupported Encoding").log(Level.SEVERE, null, exception);
            throw new EncryptionException("There is a problem in our server. Please contact our technical support.");
        } catch (NoSuchAlgorithmException exception) {
            Logger.getLogger("No such algorithm").log(Level.SEVERE, null, exception);
            throw new EncryptionException("There is a problem in our server. Please contact our technical support.");
        }
        
        return digest;
    }

    /**
     * Generates an activation code for new user.
     * @return The generated activation code. Hash is accesible with key "hash" and code with no hash with "activationCode".
     */
    public static TreeMap<String, String> generateActivationCode () {
        String random = generateRandomString(64);
        String digest = shaHash(random);
        TreeMap<String, String> activationCode = new TreeMap<>();
        activationCode.put("activationCode", random);
        activationCode.put("hash", digest);
        return activationCode;
    }

    /**
     * Generates an token to reset password of user.
     * @return The generated token. Hash is accesible with key "hash" and code with no hash with "token".
     */
    public static TreeMap<String, String> generateTokenToResetPassword () {
        String random = generateRandomString(64);
        String digest = shaHash(random);
        TreeMap<String, String> tokenToResetPassword = new TreeMap<>();
        tokenToResetPassword.put("token", random);
        tokenToResetPassword.put("hash", digest);
        return tokenToResetPassword;
    }
}
