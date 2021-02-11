package com.example.extracurricular.util;

import com.example.extracurricular.db.model.User;
import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Security {
    private static final Logger log = Logger.getLogger(Security.class);

    private Security() {
    }

    public static String sha512(User user) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            return "";
        }
        digest.update((user.getLogin() + ":" + user.getPassword()).getBytes(StandardCharsets.UTF_8));
        byte[] hash = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
