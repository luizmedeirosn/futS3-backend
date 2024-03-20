package com.luizmedeirosn.futs3.projections.player;

import java.sql.Blob;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "playerId", "playerName", "playerAge", "playerHeight", "positionId", "positionName", "playerTeam",
        "playerProfilePicture" })
public interface PlayerProjection {

    Long getPlayerId();
    String getPlayerName();
    Integer getPlayerAge();
    Integer getPlayerHeight();
    Long getPositionId();
    String getPositionName();
    String getPositionDescription();
    String getPlayerTeam();
    Blob getPlayerProfilePicture();
}
