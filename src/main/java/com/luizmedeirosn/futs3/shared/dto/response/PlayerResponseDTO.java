package com.luizmedeirosn.futs3.shared.dto.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.projections.player.PlayerParameterProjection;
import com.luizmedeirosn.futs3.services.PlayerPictureService;
import com.luizmedeirosn.futs3.shared.dto.response.min.PositionMinDTO;

@JsonPropertyOrder({ "id", "name", "age", "height", "team", "profilePictureLink", "position", "parameters" })
public record PlayerResponseDTO(

        Long id,
        String name,
        Integer age,
        Integer height,
        String team,
        List<?> parameters,
        @JsonProperty(value = "position") PositionMinDTO positionDTO,
        String profilePictureLink

) implements Serializable {

    private static final long serialVersionUID = 1L;

    public PlayerResponseDTO(Player player, List<PlayerParameterProjection> parameters) {
        this(
                player.getId(),
                player.getName(),
                player.getAge(),
                player.getHeight(),
                player.getTeam(),
                parameters,
                new PositionMinDTO(player.getPosition()),
                (player.getPlayerPicture() == null || player.getPlayerPicture().getContent() == null) ? null
                        : PlayerPictureService.createPictureLink(player.getPlayerPicture().getId()));
    }

}
