package com.luizmedeirosn.futs3.shared.dto.response;

import java.io.Serializable;
import java.util.List;

import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.entities.PlayerPicture;
import com.luizmedeirosn.futs3.projections.player.PlayerParameterProjection;
import com.luizmedeirosn.futs3.services.PlayerPictureService;
import com.luizmedeirosn.futs3.shared.dto.response.min.PositionMinDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlayerWithProjectionResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Integer age;
    private Integer height;

    private PositionMinDTO position;

    private String team;
    private String profilePictureLink;
    private List<PlayerParameterProjection> parameters;

    public PlayerWithProjectionResponseDTO(Player player, List<PlayerParameterProjection> parameters) {
        id = player.getId();
        name = player.getName();
        age = player.getAge();
        height = player.getHeight();
        team = player.getTeam();

        this.parameters = parameters;

        position = new PositionMinDTO(player.getPosition());

        PlayerPicture playerPicture = player.getPlayerPicture();
        profilePictureLink = playerPicture == null ? null
                : PlayerPictureService.createPictureLink(playerPicture.getId());
    }

}
