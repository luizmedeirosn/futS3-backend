package com.luizmedeirosn.futs3.projections.gamemode;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder( { "playerId", "playerName", "totalScore" } )
public interface PlayerScoreProjection {

    Long getPlayerId();
    String getPlayerName();
    Integer getTotalScore();
    
}
