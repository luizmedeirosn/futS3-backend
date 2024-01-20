package com.luizmedeirosn.futs3.shared.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SigninRequestDTO(

        @NotNull @NotBlank String username,
        @NotBlank @NotBlank String password

) {
}
