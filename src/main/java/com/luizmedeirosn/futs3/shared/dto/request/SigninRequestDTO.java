package com.luizmedeirosn.futs3.shared.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SigninRequestDTO(

        @NotNull String username,
        @NotBlank String password

) {
}
