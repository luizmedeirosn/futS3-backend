package com.luizmedeirosn.futs3.shared.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

public record SignupRequestDTO(
    @NotNull @NotBlank String username,
    @NotNull @NotBlank @Email String email,
    @NotNull @NotBlank String password,
    @NotNull @NotBlank String roles)
    implements Serializable {
  @Serial private static final long serialVersionUID = 1L;
}
