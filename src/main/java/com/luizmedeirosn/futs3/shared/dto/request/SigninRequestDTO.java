package com.luizmedeirosn.futs3.shared.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record SigninRequestDTO(

        @NotNull @NotBlank String username,
        @NotNull @NotBlank String password

) implements Serializable {
    private static final long serialVersionUID = 1L;
}
