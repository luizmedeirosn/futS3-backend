package com.luizmedeirosn.futs3.shared.dto.request;

import com.luizmedeirosn.futs3.shared.dto.request.aux.PlayerParameterIdScoreDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

public record PlayerRequestDTO(

        @NotNull @Size(min = 3, max = 30, message = "Enter a name between 3 and 30 characters") String name,
        @NotNull @Size(min = 3, max = 30, message = "Enter a team between 3 and 30 characters") String team,
        @Min(1) @Max(150) Integer age,
        @Min(65) @Max(250) Integer height,
        @NotNull Long positionId,
        @NotNull @Valid List<PlayerParameterIdScoreDTO> parameters,
        MultipartFile playerPicture

) implements Serializable {
    private static final long serialVersionUID = 1L;
}
