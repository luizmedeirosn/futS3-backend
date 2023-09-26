package com.luizmedeirosn.futs3.services.exceptions;

public class DatabaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DatabaseException(String messageError) {
        super(messageError);
    }
    
}
