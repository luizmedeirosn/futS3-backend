package com.luizmedeirosn.futs3.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Check;

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
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Entity
@Table(name = "tb_player")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
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

    @OneToMany(mappedBy = "id.player", cascade = CascadeType.REMOVE)
    @Singular
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

    public void updateData(String name, Position position) {
        this.name = name;
        this.position = position;
    }

}
