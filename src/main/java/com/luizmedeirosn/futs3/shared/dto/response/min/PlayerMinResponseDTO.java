package com.luizmedeirosn.futs3.shared.dto.response.min;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.projections.player.PlayerProjection;
import com.luizmedeirosn.futs3.services.PlayerService;
import java.io.Serial;
import java.io.Serializable;

@JsonPropertyOrder({"id", "name", "pictureUrl", "team", "position"})
public record PlayerMinResponseDTO(
    Long id, String name, String team, @JsonProperty(value = "position") PositionMinDTO positionDTO, String pictureUrl)
    implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  public PlayerMinResponseDTO(PlayerProjection projection) {
    this(
        projection.getPlayerId(),
        projection.getPlayerName(),
        projection.getPlayerTeam(),
        new PositionMinDTO(
            projection.getPositionId(), projection.getPositionName(), projection.getPositionDescription()),
        projection.getPlayerPicture() == null ? "" : PlayerService.createPictureUrl(projection.getPlayerId()));
  }
}
