package net.rewerk.servlets.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import net.rewerk.servlets.enumerator.UserRole;
import net.rewerk.servlets.model.User;
import net.rewerk.servlets.util.Utils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class JWTServiceTest {
    User user;

    @Before
    public void setUp() {
        user = new User(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UserRole.USER);
    }

    @After
    public void tearDown() {
        user = null;
    }

    @Test
    public void givenEncryptedJWT_whenDecodedJWTNotNull_thenSuccess() {
        String jwt = JWTService.signToken(user);

        Assert.assertNotNull(JWTService.validateToken(jwt));
    }

    @Test
    public void givenEncryptedJWT_whenDecodedJWTSubjectStringNotNull_thenSuccess() {
        String jwt = JWTService.signToken(user);
        DecodedJWT decodedJWT = JWTService.validateToken(jwt);

        Assert.assertNotNull(decodedJWT);
        Assert.assertNotNull(decodedJWT.getSubject());
    }

    @Test
    public void givenEncryptedJWT_whenDecodedJWTSubjectCheckIsValidUUID_thenSuccess() {
        String jwt = JWTService.signToken(user);
        DecodedJWT decodedJWT = JWTService.validateToken(jwt);

        Assert.assertNotNull(decodedJWT);
        Assert.assertTrue(Utils.isValidUUID(decodedJWT.getSubject()));
    }

    @Test
    public void givenEncryptedJWT_whenDecodedJWTIsValidUser_thenSuccess() {
        String jwt = JWTService.signToken(user);
        DecodedJWT decodedJWT = JWTService.validateToken(jwt);

        Assert.assertNotNull(decodedJWT);
        Assert.assertTrue(Utils.isValidUUID(decodedJWT.getSubject()));
        Assert.assertEquals(user.getId(), UUID.fromString(decodedJWT.getSubject()));
    }

    @Test
    public void givenEncryptedJWT_whenDecodedJWTIsInvalidUser_thenFailure() {
        String jwt = JWTService.signToken(user);
        user.setId(UUID.randomUUID());
        DecodedJWT decodedJWT = JWTService.validateToken(jwt);

        Assert.assertNotNull(decodedJWT);
        Assert.assertTrue(Utils.isValidUUID(decodedJWT.getSubject()));
        Assert.assertNotEquals(user.getId(), UUID.fromString(decodedJWT.getSubject()));
    }
}
