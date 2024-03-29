package com.luizmedeirosn.futs3.shared.dto.response;

import com.luizmedeirosn.futs3.entities.Position;
import com.luizmedeirosn.futs3.projections.postition.PositionParametersProjection;

import java.io.Serializable;
import java.util.List;

public record PositionResponseDTO(

        Long id,
        String name,
        String description,
        List<PositionParametersProjection> parameters

) implements Serializable {
    private static final long serialVersionUID = 1L;

    public PositionResponseDTO(Position position, List<PositionParametersProjection> positionParameters) {
        this(position.getId(), position.getName(), position.getDescription(), positionParameters);
    }
}
