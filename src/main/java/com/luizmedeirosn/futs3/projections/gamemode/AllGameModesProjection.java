package com.luizmedeirosn.futs3.projections.gamemode;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "gameModeId", "gameModeFormationName", "positionId", "positionName", "parameterId",
        "parameterName", "parameterWeight" })
public interface AllGameModesProjection {

    Long getGameModeId();

    String getGameModeFormationName();

    Long getPositionId();

    String getPositionName();

    Long getParameterId();

    String getParameterName();

    Integer getParameterWeight();

}
