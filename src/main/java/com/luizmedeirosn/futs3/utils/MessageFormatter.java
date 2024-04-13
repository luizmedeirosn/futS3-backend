package com.luizmedeirosn.futs3.utils;

import com.luizmedeirosn.futs3.shared.exceptions.FormatterException;

public final class MessageFormatter {

    public static String databaseException(String message) {
        try {
            String regex = "Detail: ";
            return message.contains(regex) ? message
                    .split(regex)[1]
                    .split("\\.")[0]
                    .replace("\"", "")
                    : message;

        } catch (IndexOutOfBoundsException e) {
            throw new FormatterException("The exception message formatting failed");
        }
    }

    public static String methodArgumentNotValidException(String message) {
        try {
            return message
                    .split(": \\[")[1]
                    .split(";")[0]
                    .replace("\"", "");

        } catch (IndexOutOfBoundsException e) {
            throw new FormatterException("The exception message formatting failed");
        }
    }
}
