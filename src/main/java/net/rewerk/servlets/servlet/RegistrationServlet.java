package net.rewerk.servlets.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.rewerk.servlets.enumerator.UserRole;
import net.rewerk.servlets.model.User;
import net.rewerk.servlets.repository.UserRepository;
import net.rewerk.servlets.service.AuthService;
import net.rewerk.servlets.service.PasswordService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegistrationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserRepository userRepository = UserRepository.getInstance();
        request.setAttribute("usersTotal", userRepository.count());
        this.getServletContext().getRequestDispatcher("/registration.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errors = new ArrayList<>();
        boolean success = false;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("passwordConfirmation");
        if (username == null || username.isEmpty()) {
            errors.add("Username is required");
        }
        if (password == null || password.isEmpty()) {
            errors.add("Password is required");
        }
        if (passwordConfirmation == null || passwordConfirmation.isEmpty()) {
            errors.add("Password confirmation is required");
        }
        if (errors.isEmpty() && !password.equals(passwordConfirmation)) {
            errors.add("Passwords do not match");
        }
        UserRepository userRepository = UserRepository.getInstance();
        if (userRepository.isExists(username)) {
            errors.add("Username is already in use");
        }
        if (errors.isEmpty()) {
            try {
                User user = new User(UUID.randomUUID(), username, PasswordService.encryptPassword(password), UserRole.USER);
                if (userRepository.save(user) == null) {
                    errors.add("User creation failed");
                } else {
                    success = true;
                }
                if (!AuthService.authenticate(user, request, response)) {
                    errors.add("Unable to authenticate user");
                }
            } catch (Exception e) {
                errors.add("Unable to create user");
            }
        }
        if (errors.isEmpty()) {
            errors = null;
        }
        request.setAttribute("usersTotal", userRepository.count());
        request.setAttribute("errors", errors);
        request.setAttribute("success", success);
        this.getServletContext().getRequestDispatcher("/registration.jsp").forward(request, response);
    }
}
