package com.luizmedeirosn.futs3.shared.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.projections.player.PlayerParameterProjection;
import com.luizmedeirosn.futs3.projections.player.PlayerProjection;
import com.luizmedeirosn.futs3.services.PlayerPictureService;
import com.luizmedeirosn.futs3.shared.dto.response.aux.PlayerParameterDataDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.PositionMinDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({ "id", "name", "age", "height", "team", "pictureUrl", "position", "parameters" })
public record PlayerResponseDTO(

        Long id,
        String name,
        Integer age,
        Integer height,
        String team,
        @JsonProperty(value = "position")
        PositionMinDTO positionDTO,
        List<?> parameters,
        String pictureUrl
) implements Serializable {

    private static final long serialVersionUID = 1L;

    public PlayerResponseDTO(PlayerProjection player, List<PlayerParameterDataDTO> parameters) {
        this(
                player.getPlayerId(),
                player.getPlayerName(),
                player.getPlayerAge(),
                player.getPlayerHeight(),
                player.getPlayerTeam(),
                new PositionMinDTO(
                    player.getPositionId(),
                    player.getPositionName(),
                    player.getPositionDescription()
                ),
                parameters,
                (
                    player.getPlayerPicture() == null || player.getPlayerPicture() == null) ?
                        "" : PlayerPictureService.createPictureUrl(player.getPlayerId()
                )
        );
    }
}
