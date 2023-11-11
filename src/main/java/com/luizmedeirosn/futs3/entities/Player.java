package com.luizmedeirosn.futs3.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Check;

import jakarta.annotation.Nonnull;
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

@Entity
@Table(name = "tb_player")
public class Player implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Nonnull
    @Column(nullable = false, unique = true)
    private String name;
    
    @Check(constraints = "age >= 1 AND age <= 150")
    private Integer age;

    @Check(constraints = "height >= 65 AND height <= 250")
    private Integer height;

    @Nonnull
    @Column(nullable = false)
    private String team;

    @ManyToOne(optional = false)
    @JoinColumn(name = "position_id")
    private Position position;

    @OneToMany(mappedBy = "id.player", cascade = CascadeType.REMOVE)
    private Set<PlayerParameter> playerParameters = new HashSet<>();

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL)
    private PlayerPicture playerPicture;

    public Player() {
    }

    public Player(String name, Integer age, Integer height, String team , Position position) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.team = team;
        this.position = position;
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

    public void updateData(String name, Position position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Player other = (Player) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
