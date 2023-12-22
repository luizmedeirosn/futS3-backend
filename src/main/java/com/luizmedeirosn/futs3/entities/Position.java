package com.luizmedeirosn.futs3.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.luizmedeirosn.futs3.shared.dto.request.update.UpdatePositionDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_position")
@NoArgsConstructor
@AllArgsConstructor
@Data
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
    private Set<Player> players = new HashSet<>();

    @ManyToMany(mappedBy = "positions")
    private Set<GameMode> gameModes = new HashSet<>();

    @OneToMany(mappedBy = "id.position")
    private Set<PositionParameter> positionParameters = new HashSet<>();

    public Position(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void updateData(UpdatePositionDTO obj) {
        name = obj.getName();
        description = obj.getDescription();
    }

}
