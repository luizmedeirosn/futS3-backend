package com.luizmedeirosn.futs3.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Check;

import com.luizmedeirosn.futs3.shared.dto.request.PlayerRequestDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_player")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Check(constraints = "age >= 1 AND age <= 150")
    private Integer age;

    @Check(constraints = "height >= 65 AND height <= 250")
    private Integer height;

    @Column(nullable = false)
    private String team;

    @ManyToOne(optional = false)
    @JoinColumn(name = "position_id")
    private Position position;

    @OneToMany(mappedBy = "id.player", cascade = CascadeType.ALL)
    private Set<PlayerParameter> playerParameters = new HashSet<>();

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL)
    private PlayerPicture playerPicture;

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

    public void updateData(PlayerRequestDTO playerRequestDTO) {
        name = playerRequestDTO.name();
        age = playerRequestDTO.age();
        height = playerRequestDTO.height();
        team = playerRequestDTO.team();
        if (playerRequestDTO.playerPicture() != null) {
            if (playerPicture == null) {
                setPlayerPicture(new PlayerPicture(this, playerRequestDTO.playerPicture()));
            } else {
                playerPicture.updateData(playerRequestDTO.playerPicture());
            }
        }
    }

}
