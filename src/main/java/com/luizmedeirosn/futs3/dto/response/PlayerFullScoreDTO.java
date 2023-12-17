package com.luizmedeirosn.futs3.dto.response;

import java.io.Serializable;
import java.sql.Blob;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.luizmedeirosn.futs3.projections.gamemode.PlayerFullScoreProjection;
import com.luizmedeirosn.futs3.projections.player.PlayerParameterProjection;
import com.luizmedeirosn.futs3.services.PlayerPictureService;

@JsonPropertyOrder({ "id", "name", "profilePictureLink", "age", "height", "team", "totalScore", "parameters" })
public class PlayerFullScoreDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String profilePictureLink;
    private Integer age;
    private Integer height;
    private String team;
    private Integer totalScore;
    private List<PlayerParameterProjection> parameters;

    public PlayerFullScoreDTO(PlayerFullScoreProjection player, List<PlayerParameterProjection> parameters) {
        id = player.getPlayerId();
        name = player.getPlayerName();
        age = player.getPlayerAge();
        height = player.getPlayerHeight();
        team = player.getPlayerTeam();
        totalScore = player.getTotalScore();
        this.parameters = parameters;

        Blob playerPicture = player.getPlayerProfilePicture();
        profilePictureLink = playerPicture == null ? null
                : PlayerPictureService.createPictureLink(player.getPlayerId());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfilePictureLink() {
        return profilePictureLink;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getHeight() {
        return height;
    }

    public String getTeam() {
        return team;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public List<PlayerParameterProjection> getParameters() {
        return parameters;
    }

}
