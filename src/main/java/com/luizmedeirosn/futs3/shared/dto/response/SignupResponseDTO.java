package com.luizmedeirosn.futs3.shared.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.entities.CustomUser;
import java.io.Serial;
import java.io.Serializable;

@JsonPropertyOrder({
  "user", "tokens",
})
public record SignupResponseDTO(CustomUser user, TokenResponseDTO tokens) implements Serializable {
  @Serial private static final long serialVersionUID = 1L;
}
