package com.luizmedeirosn.futs3.shared.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.projections.player.PlayerParameterProjection;
import com.luizmedeirosn.futs3.services.PlayerPictureService;
import com.luizmedeirosn.futs3.shared.dto.request.aux.PlayerParameterScoreDTO;
import com.luizmedeirosn.futs3.shared.dto.response.aux.PlayerParameterDTO;
import com.luizmedeirosn.futs3.shared.dto.response.min.PositionMinDTO;

import java.io.Serializable;
import java.util.Comparator;
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
                new PositionMinDTO(player.getPosition()),

                player
                        .getPlayerParameters()
                        .stream()
                        .map(PlayerParameterDTO::new)
//                        .sorted(Comparator.comparing(PlayerParameterDTO::name))
                        .toList(),

                (player.getPlayerPicture() == null || player.getPlayerPicture().getContent() == null) ?
                        "" : PlayerPictureService.createPictureLink(player.getPlayerPicture().getId()));
    }

}
