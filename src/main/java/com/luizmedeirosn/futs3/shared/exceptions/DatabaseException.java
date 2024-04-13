package com.luizmedeirosn.futs3.shared.exceptions;

public class DatabaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DatabaseException(String message) {
        super(message);
    }

    public static String formatMessage(String message) {
        final String regex = "Detail: ";
        return message.contains(regex) ? message
                .split("Detail: ")[1]
                .split("\\.")[0]
                .replace("\"", "")
                : message;
    }
}
