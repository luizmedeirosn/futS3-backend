package com.luizmedeirosn.futs3.shared.exceptions;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

public record StandardError(

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT") Instant timestamp,
        Integer status,
        String error,
        String message,
        String path

) implements Serializable {

    private static final long serialVersionUID = 1L;

}
