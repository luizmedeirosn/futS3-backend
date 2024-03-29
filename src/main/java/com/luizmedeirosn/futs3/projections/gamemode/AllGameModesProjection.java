package com.luizmedeirosn.futs3.projections.gamemode;

public interface AllGameModesProjection {

    Long getGameModeId();
    String getGameModeFormationName();
    Long getPositionId();
    String getPositionName();
    Long getParameterId();
    String getParameterName();
    Integer getParameterWeight();
}
