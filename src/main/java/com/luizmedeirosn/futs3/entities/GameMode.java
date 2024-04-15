package com.luizmedeirosn.futs3.entities;

import com.luizmedeirosn.futs3.shared.dto.request.GameModeRequestDTO;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_gamemode")
public class GameMode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "formation_name", length = 50, nullable = false, unique = true)
    private String formationName;

    @Column(columnDefinition = "TEXT", length = 2000)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tb_gamemode_position", joinColumns = @JoinColumn(name = "gamemode_id"), inverseJoinColumns = @JoinColumn(name = "position_id"))
    private final Set<Position> positions = new HashSet<>();

    public GameMode() {
    }

    public GameMode(Long id, String formationName, String description) {
        this.id = id;
        this.formationName = formationName;
        this.description = description;
    }

    public GameMode(String formationName, String description) {
        this.formationName = formationName;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormationName() {
        return formationName;
    }

    public void setFormationName(String formationName) {
        this.formationName = formationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public void updateData(GameModeRequestDTO gameModeRequestDTO) {
        formationName = gameModeRequestDTO.formationName();
        description = gameModeRequestDTO.description();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameMode gameMode)) return false;
        return Objects.equals(id, gameMode.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
