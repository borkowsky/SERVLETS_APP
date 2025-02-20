package net.rewerk.servlets.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.rewerk.servlets.model.User;
import net.rewerk.servlets.repository.UserRepository;
import net.rewerk.servlets.service.JWTService;
import net.rewerk.servlets.util.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class AuthFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            request.setAttribute("authenticated", false);
            request.setAttribute("user", null);
            chain.doFilter(request, response);
            return;
        }
        Cookie authCookie = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("__authToken")).findFirst().orElse(null);
        boolean authenticated = false;
        User user = null;
        if (authCookie != null) {
            String authToken = authCookie.getValue();
            DecodedJWT jwt = JWTService.validateToken(authToken);
            if (jwt != null) {
                UserRepository userRepository = UserRepository.getInstance();
                String subject = jwt.getSubject();
                if (subject != null && !subject.isEmpty() && Utils.isValidUUID(subject)) {
                    UUID userId = UUID.fromString(jwt.getSubject());
                    user = userRepository.findById(userId);
                }
                if (user != null) {
                    authenticated = true;
                }
            }
        }
        request.setAttribute("authenticated", authenticated);
        request.setAttribute("user", user);
        chain.doFilter(request, response);
    }
}
