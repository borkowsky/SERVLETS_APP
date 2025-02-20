package net.rewerk.servlets.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import net.rewerk.servlets.exception.AuthException;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public abstract class PasswordService {
    private static final Integer STRENGTH = 6;

    public static String encryptPassword(String password) {
        return BCrypt.with(new SecureRandom())
                .hashToString(STRENGTH, password.toCharArray());
    }

    public static boolean verifyPassword(String password, String hashedPassword) throws AuthException {
        return BCrypt.verifyer()
                .verify(password.getBytes(StandardCharsets.UTF_8), hashedPassword.getBytes(StandardCharsets.UTF_8))
                .verified;
    }
}
