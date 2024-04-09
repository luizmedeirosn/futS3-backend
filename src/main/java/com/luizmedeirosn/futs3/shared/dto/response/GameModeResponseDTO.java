package com.luizmedeirosn.futs3.shared.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

@JsonPropertyOrder({ "id", "formationName", "description", "positions" })
public record GameModeResponseDTO(

        Long id,
        String formationName,
        String description,
        List<PositionResponseDTO> positions

) implements Serializable {
    private static final long serialVersionUID = 1L;
}
