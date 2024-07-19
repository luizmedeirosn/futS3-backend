package com.luizmedeirosn.futs3.shared.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.projections.gamemode.PlayerDataScoreProjection;
import com.luizmedeirosn.futs3.services.PlayerService;
import com.luizmedeirosn.futs3.shared.dto.response.aux.PlayerParameterDataDTO;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@JsonPropertyOrder({"id", "name", "age", "height", "team", "pictureUrl", "totalScore", "parameters"})
public record PlayerFullDataResponseDTO(
    Long id,
    String name,
    Integer age,
    Integer height,
    String team,
    Integer totalScore,
    List<PlayerParameterDataDTO> parameters,
    String pictureUrl)
    implements Serializable {
  @Serial private static final long serialVersionUID = 1L;

  public PlayerFullDataResponseDTO(PlayerDataScoreProjection player, List<PlayerParameterDataDTO> parameters) {
    this(
        player.getPlayerId(),
        player.getPlayerName(),
        player.getPlayerAge(),
        player.getPlayerHeight(),
        player.getPlayerTeam(),
        player.getTotalScore(),
        parameters,
        player.getPlayerProfilePicture() == null ? "" : PlayerService.createPictureUrl(player.getPlayerId()));
  }
}
