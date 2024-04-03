package com.luizmedeirosn.futs3.projections.player;

import java.sql.Blob;

public interface PlayerProjection {

    Long getPlayerId();
    String getPlayerName();
    Integer getPlayerAge();
    Integer getPlayerHeight();
    String getPlayerTeam();
    Blob getPlayerPicture();
    Long getPositionId();
    String getPositionName();
    String getPositionDescription();
    Long getParameterId();
    String getParameterName();
    Integer getPlayerScore();
}
