package com.luizmedeirosn.futs3.shared.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.projections.player.PlayerParameterProjection;
import com.luizmedeirosn.futs3.projections.player.PlayerProjection;
import com.luizmedeirosn.futs3.services.PlayerPictureService;
import com.luizmedeirosn.futs3.shared.dto.response.aux.PlayerParameterDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.PositionMinDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({ "id", "name", "age", "height", "team", "profilePictureLink", "position", "parameters" })
public record PlayerResponseDTO(

        Long id,
        String name,
        Integer age,
        Integer height,
        String team,
        @JsonProperty(value = "position")
        PositionMinDTO positionDTO,
        List<?> parameters,
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
                new PositionMinDTO(player.getPosition()),
                parameters,
                (player.getPlayerPicture() == null || player.getPlayerPicture().getContent() == null) ?
                        "" : PlayerPictureService.createPictureLink(player.getPlayerPicture().getId()));
    }

    public PlayerResponseDTO(Player player) {
        this(
                player.getId(),
                player.getName(),
                player.getAge(),
                player.getHeight(),
                player.getTeam(),
                null,
                null,
                (player.getPlayerPicture() == null || player.getPlayerPicture().getContent() == null) ?
                        "" : PlayerPictureService.createPictureLink(player.getId()));
    }

    public PlayerResponseDTO(PlayerProjection player) {
        this(
                player.getPlayerId(),
                player.getPlayerName(),
                player.getPlayerAge(),
                player.getPlayerHeight(),
                player.getPlayerTeam(),
                new PositionMinDTO(player.getPositionId(), player.getPositionName(), player.getPositionDescription()),
                new ArrayList<PlayerParameterDTO>(),
                player.getPlayerProfilePicture() == null ?
                        "" : PlayerPictureService.createPictureLink(player.getPlayerId()));
    }

}
