package com.luizmedeirosn.futs3.shared.dto.request;

import java.io.Serializable;
import java.util.List;

import com.luizmedeirosn.futs3.shared.dto.request.aux.PositionParametersDTO;

public record GameModeRequestDTO(

        String formationName,
        String description,
        List<PositionParametersDTO> positionsParameters

) implements Serializable {

    private static final long serialVersionUID = 1L;

}
