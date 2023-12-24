package com.luizmedeirosn.futs3.shared.dto.request.aux;

import java.io.Serializable;
import java.util.List;

public record PositionParametersDTO(

        Long positionId,
        List<ParameterWeightDTO> parameters

) implements Serializable {

    private static final long serialVersionUID = 1L;

}
