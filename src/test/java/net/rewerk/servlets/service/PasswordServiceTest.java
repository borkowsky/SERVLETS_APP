package net.rewerk.servlets.service;

import org.junit.Assert;
import org.junit.Test;

public class PasswordServiceTest {
    @Test
    public void givenPassword_whenEncryptPasswordNotNull_thenSuccess() {
        String password = PasswordService.encryptPassword("password");

        Assert.assertNotNull(password);
    }

    @Test
    public void givenPassword_whenDecryptPasswordCorrect_thenSuccess() {
        Assert.assertTrue(PasswordService.verifyPassword("password",
                PasswordService.encryptPassword("password")));
    }

    @Test
    public void givenPassword_whenDecryptPasswordIncorrect_thenFailure() {
        Assert.assertFalse(PasswordService.verifyPassword("password",
                "dummyHash"));
    }
}
