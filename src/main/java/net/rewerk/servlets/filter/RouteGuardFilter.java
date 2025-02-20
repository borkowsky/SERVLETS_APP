package net.rewerk.servlets.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.rewerk.servlets.model.User;

import java.io.IOException;
import java.util.Arrays;

public class RouteGuardFilter implements Filter {
    private final String[] PUBLIC_ROUTES = {"/"};
    private final String[] GUEST_ROUTES = {"/login", "/registration"};
    private final String[] PRIVATE_ROUTES = {"/books", "/book", "/user"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Boolean authenticated = (Boolean) request.getAttribute("authenticated");
        User user = (User) request.getAttribute("user");
        String requestURI = String.format("%s?%s",
                httpRequest.getRequestURI(),
                httpRequest.getQueryString());
        if (authenticated && user != null) {
            if (Arrays.stream(GUEST_ROUTES).anyMatch(requestURI::startsWith)) {
                redirect(httpRequest, httpResponse, PUBLIC_ROUTES[0]);
                return;
            }
        } else {
            if (Arrays.stream(PRIVATE_ROUTES).anyMatch(requestURI::startsWith)) {
                redirect(httpRequest, httpResponse, PUBLIC_ROUTES[0]);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    public void redirect(HttpServletRequest req, HttpServletResponse res, String to) throws IOException {
        res.sendRedirect(req.getContextPath() + to);
    }
}
