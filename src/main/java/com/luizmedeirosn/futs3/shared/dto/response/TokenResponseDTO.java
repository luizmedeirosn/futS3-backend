package com.luizmedeirosn.futs3.shared.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serial;
import java.io.Serializable;

@JsonPropertyOrder({
  "accessToken",
  "refreshToken",
})
public record TokenResponseDTO(String accessToken, String refreshToken) implements Serializable {
  @Serial private static final long serialVersionUID = 1L;
}
