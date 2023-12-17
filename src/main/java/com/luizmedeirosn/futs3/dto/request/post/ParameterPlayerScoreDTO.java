package com.luizmedeirosn.futs3.dto.request.post;

import java.io.Serializable;

public class ParameterPlayerScoreDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer playerScore;

    public ParameterPlayerScoreDTO() {
    }

    public ParameterPlayerScoreDTO(Long id, Integer playerScore) {
        this.id = id;
        this.playerScore = playerScore;
    }

    public Long getId() {
        return id;
    }

    public Integer getPlayerScore() {
        return playerScore;
    }

}
