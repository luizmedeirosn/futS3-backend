package com.luizmedeirosn.futs3.shared.exceptions;

public class PageableException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PageableException(String message) {
        super(message);
    }
}