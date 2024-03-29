package com.luizmedeirosn.futs3.projections.gamemode;

import java.sql.Blob;

public interface PlayerFullScoreProjection {

    Long getPlayerId();
    String getPlayerName();
    Blob getPlayerProfilePicture();
    Integer getPlayerAge();
    Integer getPlayerHeight();
    String getPlayerTeam();
    Integer getTotalScore();
}
