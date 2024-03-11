package com.luizmedeirosn.futs3.shared.dto.response.aux;

import com.luizmedeirosn.futs3.entities.PlayerParameter;

import java.io.Serializable;

public record PlayerParameterDTO(

        Long id,
        String name,
        Integer score

) implements Serializable {

    private static final long serialVersionUID = 1L;

    public PlayerParameterDTO(PlayerParameter playerParameter) {
        this(
                playerParameter.getId().getParameter().getId(),
                playerParameter.getId().getParameter().getName(),
                playerParameter.getScore()
        );
    }

}
