package com.luizmedeirosn.futs3.projections;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder( { "playerId", "playerName", "positionId", "positionName" } )
public interface PlayerProjection {
    
    Long getPlayerId();
    String getPlayerName();
    Long getPositionId();
    String getPositionName();

}
