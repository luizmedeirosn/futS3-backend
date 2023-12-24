package com.luizmedeirosn.futs3.shared.dto.response.min;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.projections.player.PlayerProjection;
import com.luizmedeirosn.futs3.services.PlayerPictureService;

@JsonPropertyOrder({ "id", "name", "position", "profilePictureLink" })
public record PlayerMinResponseDTO(

        Long id,
        String name,
        @JsonProperty(value = "position") PositionMinDTO positionDTO,
        String profilePictureLink

) implements Serializable {

    private static final long serialVersionUID = 1L;

    public PlayerMinResponseDTO(Player player) {
        this(
                player.getId(),
                player.getName(),
                new PositionMinDTO(player.getPosition()),
                player.getPlayerPicture() == null ? null
                        : PlayerPictureService.createPictureLink(player.getPlayerPicture().getId()));
    }

    public PlayerMinResponseDTO(PlayerProjection playerProjection) {
        this(
                playerProjection.getPlayerId(),
                playerProjection.getPlayerName(),
                new PositionMinDTO(playerProjection.getPositionId(), playerProjection.getPositionName()),
                playerProjection.getPlayerProfilePicture() == null ? ""
                        : PlayerPictureService.createPictureLink(playerProjection.getPlayerId()));
    }

}
