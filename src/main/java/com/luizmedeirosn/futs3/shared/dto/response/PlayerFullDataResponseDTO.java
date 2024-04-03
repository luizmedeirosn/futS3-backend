package com.luizmedeirosn.futs3.shared.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.projections.gamemode.PlayerFullScoreProjection;
import com.luizmedeirosn.futs3.projections.player.PlayerParameterProjection;
import com.luizmedeirosn.futs3.services.PlayerPictureService;
import org.springframework.lang.NonNull;

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
        List<PlayerParameterProjection> parameters,
        String pictureUrl

) implements Serializable {
    private static final long serialVersionUID = 1L;

    public PlayerFullDataResponseDTO(@NonNull PlayerFullScoreProjection player,
                                     List<PlayerParameterProjection> parameters) {
        this(
                player.getPlayerId(),
                player.getPlayerName(),
                player.getPlayerAge(),
                player.getPlayerHeight(),
                player.getPlayerTeam(),
                player.getTotalScore(),
                parameters,
                player.getPlayerProfilePicture() == null ? null
                        : PlayerPictureService.createPictureUrl(player.getPlayerId()));
    }
}
