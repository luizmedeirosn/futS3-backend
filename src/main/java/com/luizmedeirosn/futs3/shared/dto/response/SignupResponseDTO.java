package com.luizmedeirosn.futs3.shared.dto.response;

import com.luizmedeirosn.futs3.entities.CustomUser;

public record SignupResponseDTO(

        CustomUser user,
        TokenResponseDTO tokens

) {
}
