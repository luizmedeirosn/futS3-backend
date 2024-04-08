package com.luizmedeirosn.futs3.shared.dto.response;

import com.luizmedeirosn.futs3.projections.postition.PositionParametersProjection;
import com.luizmedeirosn.futs3.shared.dto.response.aux.PositionParametersDataDTO;

import java.io.Serializable;
import java.util.List;

public record PositionResponseDTO(

        Long id,
        String name,
        String description,
        List<PositionParametersDataDTO> parameters

) implements Serializable {
    private static final long serialVersionUID = 1L;

    public PositionResponseDTO(PositionParametersProjection position, List<PositionParametersDataDTO> parameters) {
        this(position.getId(), position.getName(), position.getDescription(), parameters);
    }
}
