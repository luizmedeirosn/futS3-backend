package com.luizmedeirosn.futs3.dto.output.min;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.dto.output.PositionDTO;
import com.luizmedeirosn.futs3.entities.Player;

@JsonPropertyOrder( { "id", "name", "position" } )
public class PlayerMinDTO implements Serializable, Comparable<PlayerMinDTO>{
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;

    @JsonProperty(value = "position")
    private PositionDTO positionDTO;
    
    public PlayerMinDTO() {
    }
    
    public PlayerMinDTO(Player player) {
        id = player.getId();
        name = player.getName();
        positionDTO = new PositionDTO(player.getPosition());
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

    @Override
    public int compareTo(PlayerMinDTO other) {
        return name.compareTo(other.getName());
    }
    
}

