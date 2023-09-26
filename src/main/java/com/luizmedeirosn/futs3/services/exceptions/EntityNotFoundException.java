package com.luizmedeirosn.futs3.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String messageEroor) {
        super(messageEroor);
    }
}
