package com.luizmedeirosn.futs3.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.luizmedeirosn.futs3.shared.dto.request.update.UpdateGameModeDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_gamemode")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GameMode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "formation_name", nullable = false, unique = true)
    private String formationName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tb_gamemode_position", joinColumns = @JoinColumn(name = "gamemode_id"), inverseJoinColumns = @JoinColumn(name = "position_id"))
    private Set<Position> positions = new HashSet<>();

    public GameMode(String formationName, String description) {
        this.formationName = formationName;
        this.description = description;
    }

    public void updateData(UpdateGameModeDTO updateGameModeDTO) {
        formationName = updateGameModeDTO.getFormationName();
        description = updateGameModeDTO.getDescription();
    }

}
