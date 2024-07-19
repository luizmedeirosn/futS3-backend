package com.luizmedeirosn.futs3.shared.dto.response.min;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.entities.GameMode;
import java.io.Serial;
import java.io.Serializable;

@JsonPropertyOrder({"id", "formationName", "description"})
public record GameModeMinResponseDTO(Long id, String formationName, String description) implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  public GameModeMinResponseDTO(GameMode gameMode) {
    this(gameMode.getId(), gameMode.getFormationName(), gameMode.getDescription());
  }
}
