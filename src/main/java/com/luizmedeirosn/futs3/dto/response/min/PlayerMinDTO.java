package com.luizmedeirosn.futs3.dto.response.min;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.dto.response.PositionDTO;
import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.entities.PlayerPicture;
import com.luizmedeirosn.futs3.projections.PlayerProjection;
import com.luizmedeirosn.futs3.services.PlayerPictureService;

@JsonPropertyOrder( { "id", "name", "position", "profilePictureLink" } )
public class PlayerMinDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;

    @JsonProperty(value = "position")
    private PositionDTO positionDTO;

    String profilePictureLink;

    public PlayerMinDTO() {
    }
    
    public PlayerMinDTO(Player player) {
        id = player.getId();
        name = player.getName();
        positionDTO = new PositionDTO(player.getPosition());
        PlayerPicture playerPicture = player.getPlayerPicture();
        profilePictureLink = playerPicture == null ? "": PlayerPictureService.createPictureLink(playerPicture.getId());
    }

    public PlayerMinDTO(PlayerProjection playerProjection) {
        id = playerProjection.getPlayerId();
        name = playerProjection.getPlayerName();
        positionDTO = new PositionDTO(playerProjection.getPositionId(), playerProjection.getPositionName());
        profilePictureLink = id == null ? "": PlayerPictureService.createPictureLink(id);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PositionDTO getPositionDTO() {
        return positionDTO;
    }

    public String getProfilePictureLink() {
        return profilePictureLink;
    }
    
}

