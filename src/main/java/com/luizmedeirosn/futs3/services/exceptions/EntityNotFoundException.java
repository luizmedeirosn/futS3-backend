package com.luizmedeirosn.futs3.services.exceptions;

public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String messageEroor) {
        super(messageEroor);
    }
}
