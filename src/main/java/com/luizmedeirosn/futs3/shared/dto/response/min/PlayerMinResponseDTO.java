package com.luizmedeirosn.futs3.shared.dto.response.min;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.projections.player.PlayerProjection;
import com.luizmedeirosn.futs3.services.PlayerService;

import java.io.Serializable;

@JsonPropertyOrder({"id", "name", "pictureUrl", "position"})
public record PlayerMinResponseDTO(

        Long id,
        String name,
        @JsonProperty(value = "position") PositionMinDTO positionDTO,
        String pictureUrl

) implements Serializable {

    private static final long serialVersionUID = 1L;

    public PlayerMinResponseDTO(PlayerProjection playerProjection) {
        this(
                playerProjection.getPlayerId(),
                playerProjection.getPlayerName(),
                new PositionMinDTO(
                        playerProjection.getPositionId(),
                        playerProjection.getPositionName(),
                        playerProjection.getPositionDescription()
                ),
                playerProjection.getPlayerPicture() == null ?
                        "" : PlayerService.createPictureUrl(playerProjection.getPlayerId()));
    }
}
