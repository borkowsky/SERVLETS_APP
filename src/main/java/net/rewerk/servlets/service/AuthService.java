package net.rewerk.servlets.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.rewerk.servlets.exception.AuthException;
import net.rewerk.servlets.model.User;

public abstract class AuthService {
    public static boolean authenticate(
            User user,
            HttpServletRequest request,
            HttpServletResponse response) throws AuthException {
        try {
            String token = JWTService.signToken(user);
            Cookie cookie = getAuthCookie(token);
            response.addCookie(cookie);
            return true;
        } catch (Exception e) {
            throw new AuthException("Unable to authenticate user");
        }
    }

    public static void logout(HttpServletRequest request, HttpServletResponse response) throws AuthException {
        Cookie cookie = getAuthCookie(null);
        HttpSession session = request.getSession(false);
        session.invalidate();
        response.addCookie(cookie);
    }

    private static Cookie getAuthCookie(String cookieString) {
        Cookie cookie = new Cookie("__authToken", cookieString);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 5 * 60 * 60);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        return cookie;
    }
}
