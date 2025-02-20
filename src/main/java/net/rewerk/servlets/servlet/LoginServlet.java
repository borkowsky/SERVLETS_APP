package net.rewerk.servlets.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.rewerk.servlets.model.User;
import net.rewerk.servlets.repository.UserRepository;
import net.rewerk.servlets.service.AuthService;
import net.rewerk.servlets.service.PasswordService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || username.isEmpty()) {
            errors.add("Username cannot be empty");
        }
        if (password == null || password.isEmpty()) {
            errors.add("Password cannot be empty");
        }
        if (errors.isEmpty()) {
            UserRepository userRepository = UserRepository.getInstance();
            if (!userRepository.isExists(username)) {
                errors.add("User not found");
            } else {
                User user = userRepository.findByUsername(username);
                if (user == null) {
                    errors.add("User not found");
                } else {
                    if (!PasswordService.verifyPassword(password, user.getPassword())) {
                        errors.add("Incorrect password");
                    } else {
                        if (AuthService.authenticate(user, request, response)) {
                            request.setAttribute("success", true);
                        }
                    }
                }
            }
        }
        if (errors.isEmpty()) errors = null;
        request.setAttribute("errors", errors);
        this.getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
