package com.luizmedeirosn.futs3.shared.dto.response.aux;

import java.io.Serializable;

public record GameModePositionParameterDTO(

        Long positionId,
        String positionName,
        Long parameterId,
        String parameterName,
        Integer parameterWeight

) implements Serializable {

    private static final long serialVersionUID = 1L;

}
