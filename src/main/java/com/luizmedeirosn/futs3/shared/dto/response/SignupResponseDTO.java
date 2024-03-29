package com.luizmedeirosn.futs3.shared.dto.response;

import com.luizmedeirosn.futs3.entities.CustomUser;

import java.io.Serializable;

public record SignupResponseDTO(

        CustomUser user,
        TokenResponseDTO tokens

) implements Serializable {
    private static final long serialVersionUID = 1L;
}
