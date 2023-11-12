package com.luizmedeirosn.futs3.projections.gamemode;

import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder( { "playerId", "playerName", "playerProfilePicture", "playerAge", "playerHeight", "playerTeam", "totalScore" } )
public interface PlayerFullScoreProjection {

    Long getPlayerId();
    String getPlayerName();
    Blob getPlayerProfilePicture();
    Integer getPlayerAge();
    Integer getPlayerHeight();
    String getPlayerTeam();
    Integer getTotalScore();

}
