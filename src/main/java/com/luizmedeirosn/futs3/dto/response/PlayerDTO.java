package com.luizmedeirosn.futs3.dto.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.dto.response.min.PositionMinDTO;
import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.entities.PlayerPicture;
import com.luizmedeirosn.futs3.projections.PlayerParameterProjection;
import com.luizmedeirosn.futs3.services.PlayerPictureService;

@JsonPropertyOrder( { "id", "name", "position", "profilePictureLink", "parameters" } )
public class PlayerDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String name;

    @JsonProperty(value = "position")
    private PositionMinDTO positionDTO;

    private String profilePictureLink;
    private List<PlayerParameterProjection> parameters;
    
    public PlayerDTO() {
    }
    
    public PlayerDTO(Player player, List<PlayerParameterProjection> parameters) {
        id = player.getId();
        name = player.getName();
        positionDTO = new PositionMinDTO(player.getPosition());
        PlayerPicture playerPicture = player.getPlayerPicture();
        profilePictureLink = playerPicture == null ? "": PlayerPictureService.createPictureLink(playerPicture.getId());
        this.parameters = parameters;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PositionMinDTO getPositionDTO() {
        return positionDTO;
    }

    public List<PlayerParameterProjection> getParameters() {
        return parameters;
    }

    public String getProfilePictureLink() {
        return profilePictureLink;
    }

}
