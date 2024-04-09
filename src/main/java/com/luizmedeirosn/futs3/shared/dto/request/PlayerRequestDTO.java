package com.luizmedeirosn.futs3.shared.dto.request;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public record PlayerRequestDTO(

        @NotNull @Size(min = 3, max = 30, message = "Enter a name between 3 and 30 characters") String name,
        @NotNull @Size(min = 3, max = 30, message = "Enter a team between 3 and 30 characters") String team,
        @Min(1) @Max(150) Integer age,
        @Min(65) @Max(250) Integer height,
        @NotNull Long positionId,

        /*
         * @param parameters JSON string representing an array of objects with "id" and "score" fields.
         *          [
         *              {"id":20,"score":50},
         *              {"id":15,"score":40},
         *              {"id":19,"score":30},
         *              {"id":21,"score":20},
         *              {"id":25,"score":10}
         *           ]
         * @throws IllegalArgumentException if the parameters string is null or empty.
         * @throws IllegalArgumentException if the parameters string is not a valid JSON array of objects.
         * @throws IllegalArgumentException if any object in the array doesn't have "id" and "score" fields.
         */
        @NotNull String parameters,

        MultipartFile playerPicture

) implements Serializable {
    private static final long serialVersionUID = 1L;
}
