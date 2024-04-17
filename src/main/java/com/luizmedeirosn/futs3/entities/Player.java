package com.luizmedeirosn.futs3.entities;

import com.luizmedeirosn.futs3.shared.dto.request.PlayerRequestDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_player")
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Check(constraints = "age >= 1 AND age <= 150")
    private Integer age;

    @Check(constraints = "height >= 65 AND height <= 250")
    private Integer height;

    @Column(length = 30, nullable = false)
    private String team;

    @ManyToOne(optional = false,  fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL)
    private PlayerPicture playerPicture;

    @OneToMany(mappedBy = "id.player", fetch = FetchType.LAZY)
    private final Set<PlayerParameter> playerParameters = new HashSet<>();

    public Player() {
    }

    public Player(
            Long id, String name, Integer age, Integer height,
            String team, Position position, PlayerPicture playerPicture
    ) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.height = height;
        this.team = team;
        this.position = position;
        this.playerPicture = playerPicture;
    }

    public Player(String name, Integer age, Integer height, String team, Position position) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.team = team;
        this.position = position;
    }

    public Player(PlayerRequestDTO playerRequestDTO) {
        name = playerRequestDTO.name();
        age = playerRequestDTO.age();
        height = playerRequestDTO.height();
        team = playerRequestDTO.team();
    }

    public void updateData(PlayerRequestDTO playerRequestDTO, Position position) {
        name = playerRequestDTO.name();
        age = playerRequestDTO.age();
        height = playerRequestDTO.height();
        team = playerRequestDTO.team();
        this.position = position;
        if (playerRequestDTO.playerPicture() != null) {
            if (playerPicture == null) {
                setPlayerPicture(new PlayerPicture(this, playerRequestDTO.playerPicture()));
            } else {
                playerPicture.updateData(playerRequestDTO.playerPicture());
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Set<PlayerParameter> getPlayerParameters() {
        return playerParameters;
    }

    public PlayerPicture getPlayerPicture() {
        return playerPicture;
    }

    public void setPlayerPicture(PlayerPicture playerPicture) {
        this.playerPicture = playerPicture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
