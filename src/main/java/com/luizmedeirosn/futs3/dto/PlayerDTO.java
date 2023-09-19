package com.luizmedeirosn.futs3.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.entities.Player;
import com.luizmedeirosn.futs3.projections.ParameterProjection;

@JsonPropertyOrder( { "id", "name", "position", "parameters" } )
public class PlayerDTO implements Comparable<PlayerDTO>{
    
    private Long id;
    private String name;

    @JsonProperty(value = "position")
    private PositionDTO positionDTO;

    private List<ParameterProjection> parameters;
    
    public PlayerDTO() {
    }
    
    public PlayerDTO(Player player, List<ParameterProjection> parameters) {
        id = player.getId();
        name = player.getName();
        positionDTO = new PositionDTO(player.getPosition());
        this.parameters = parameters;
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

    public List<ParameterProjection> getParameters() {
        return parameters;
    }

    @Override
    public int compareTo(PlayerDTO other) {
        return name.compareTo(other.getName());
    }
    
}
