package com.luizmedeirosn.futs3.shared.dto.response;

import java.io.Serializable;
import java.util.List;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.projections.gamemode.PlayerFullScoreProjection;
import com.luizmedeirosn.futs3.projections.player.PlayerParameterProjection;
import com.luizmedeirosn.futs3.services.PlayerPictureService;

@JsonPropertyOrder({ "id", "name", "age", "height", "team", "totalScore", "parameters", "profilePictureLink" })
public record PlayerFullScoreResponseDTO(

                Long id,
                String name,
                Integer age,
                Integer height,
                String team,
                Integer totalScore,
                List<PlayerParameterProjection> parameters,
                String profilePictureLink

) implements Serializable {

        private static final long serialVersionUID = 1L;

        public PlayerFullScoreResponseDTO(@NonNull PlayerFullScoreProjection player,
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
                                                : PlayerPictureService.createPictureLink(player.getPlayerId()));
        }

}
