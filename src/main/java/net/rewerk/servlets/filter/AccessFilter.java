package net.rewerk.servlets.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.rewerk.servlets.enumerator.UserRole;
import net.rewerk.servlets.model.User;

import java.io.IOException;
import java.util.Arrays;

public class AccessFilter implements Filter {
    private final String[] ADMIN_ROUTES = {"/books?action="};
    private final String[] ADMIN_METHODS = {"POST", "PUT", "DELETE"};
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        Boolean authenticated = (Boolean) request.getAttribute("authenticated");
        User user = (User) request.getAttribute("user");
        String requestMethod = req.getMethod();
        String requestUri = String.format("%s?%s",
                req.getRequestURI(),
                req.getQueryString());
        if (!authenticated || user == null || user.getRole() != UserRole.ADMIN) {
            if (Arrays.stream(ADMIN_ROUTES).anyMatch(requestUri::startsWith)) {
                res.sendRedirect(req.getContextPath() + "/books");
                return;
            }
            if (Arrays.stream(ADMIN_METHODS).anyMatch(requestMethod::equalsIgnoreCase)) {
                res.sendRedirect(req.getContextPath() + "/books");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
