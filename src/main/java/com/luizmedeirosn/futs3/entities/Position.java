package com.luizmedeirosn.futs3.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.luizmedeirosn.futs3.shared.dto.request.PositionRequestDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_position")
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "position")
    private final Set<Player> players = new HashSet<>();

    @ManyToMany(mappedBy = "positions")
    private final Set<GameMode> gameModes = new HashSet<>();

    @OneToMany(mappedBy = "id.position", cascade = CascadeType.ALL)
    private final Set<PositionParameter> positionParameters = new HashSet<>();

    public Position() {
    }

    public Position(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void updateData(PositionRequestDTO positionRequestDTO) {
        name = positionRequestDTO.name();
        description = positionRequestDTO.description();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public Set<GameMode> getGameModes() {
        return gameModes;
    }

    public Set<PositionParameter> getPositionParameters() {
        return positionParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return Objects.equals(id, position.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
