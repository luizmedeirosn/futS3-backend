package com.luizmedeirosn.futs3.projections;

import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder( { "playerId", "playerName", "playerProfilePicture", "positionId", "positionName" } )
public interface PlayerProjection {
    
    Long getPlayerId();
    String getPlayerName();
    Long getPositionId();
    String getPositionName();
    Blob getPlayerProfilePicture();

}
