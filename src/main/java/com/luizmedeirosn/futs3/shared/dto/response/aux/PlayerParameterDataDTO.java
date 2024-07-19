package com.luizmedeirosn.futs3.shared.dto.response.aux;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.projections.player.PlayerIdParameterProjection;
import java.io.Serial;
import java.io.Serializable;

@JsonPropertyOrder({"id", "name", "score"})
public record PlayerParameterDataDTO(Long id, String name, Integer score) implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  public PlayerParameterDataDTO(PlayerIdParameterProjection playerProjection) {
    this(playerProjection.getParameterId(), playerProjection.getParameterName(), playerProjection.getPlayerScore());
  }
}
