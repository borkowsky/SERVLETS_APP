package net.rewerk.servlets.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import net.rewerk.servlets.model.User;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public abstract class JWTService {
    private static JWTService instance;
    private static final String KEY = ")50A@o+s96pyZP$4*#K4,x_>HpzR4T0{P~aZ";
    private static final String ISSUER = "rewerk-servlets-app";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(KEY);

    public static String signToken(User user) {
        return JWT.create()
                .withSubject(user.getId().toString())
                .withIssuer(ISSUER)
                .withIssuedAt(new Date().toInstant())
                .withExpiresAt(Date.from(Instant.now().plus(5, ChronoUnit.DAYS)))
                .sign(ALGORITHM);
    }

    public static DecodedJWT validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(ALGORITHM)
                    .withIssuer(ISSUER)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Instant expiresAt = jwt.getExpiresAtAsInstant();
            String issuer = jwt.getIssuer();
            if (!issuer.equals(ISSUER)) {
                return null;
            }
            if (expiresAt.isBefore(Instant.now())) {
                return null;
            }
            return jwt;
        } catch (JWTVerificationException e) {
            return null;
        }
    }
}
