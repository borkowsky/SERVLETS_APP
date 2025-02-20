package net.rewerk.servlets.exception;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}
