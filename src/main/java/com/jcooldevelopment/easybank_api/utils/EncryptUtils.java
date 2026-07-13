package com.jcooldevelopment.easybank_api.utils;

import java.security.SecureRandom;

public class EncryptUtils {

    // https://www.baeldung.com/java-secure-random
    public static String generateUsercode() {
        // Possible 64 chars for usercode
        String chars = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz0123456789";
        StringBuilder usercode = new StringBuilder("");
        SecureRandom intGenerator = new SecureRandom();

        // For each of usercode, we get each char
        for(int i = 0; i<10; i++) {
            var randomNumber = intGenerator.nextInt(0,chars.length());
            usercode.insert(i, chars.charAt(randomNumber));
        }

        // Transform usercode to String
        return usercode.toString();
    }

    public static String generateActivationCode () {
        String chars = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz0123456789";
        StringBuilder code = new StringBuilder("");
        SecureRandom intGenerator = new SecureRandom();

        for(int i = 0; i<20; i++) {
            var randomNumber = intGenerator.nextInt(0,chars.length());
            code.insert(i, chars.charAt(randomNumber));
        }

        return code.toString();
    }
}
