package com.luizmedeirosn.futs3.projections.gamemode;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "positionId", "positionName" })
public interface GameModePositionProjection {

    Long getPositionId();

    String getPositionName();

}
