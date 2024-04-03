package com.luizmedeirosn.futs3.projections.gamemode;

public interface GameModePositionsParametersProjection {

    Long getPositionId();
    String getPositionName();
    Long getParameterId();
    String getParameterName();
    Integer getParameterWeight();
}
