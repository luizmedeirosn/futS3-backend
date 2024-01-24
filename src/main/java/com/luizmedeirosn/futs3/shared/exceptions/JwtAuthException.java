package com.luizmedeirosn.futs3.shared.exceptions;

public class JwtAuthException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public JwtAuthException(String message) {
        super(message);
    }
}
