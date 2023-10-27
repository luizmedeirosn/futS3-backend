package com.luizmedeirosn.futs3.projections.player;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder( { "playerId", "playerScore", "name" } )
public interface PlayerParameterProjection {
    
    Long getId();
    Integer getPlayerScore();
    String getName();

}
