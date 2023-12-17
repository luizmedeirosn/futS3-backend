package com.luizmedeirosn.futs3.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.luizmedeirosn.futs3.dto.request.update.UpdateParameterDTO;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_parameter")
public class Parameter implements Serializable, Comparable<Parameter> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "id.parameter")
    private Set<PositionParameter> positionParameters = new HashSet<>();

    @OneToMany(mappedBy = "id.parameter")
    private Set<PlayerParameter> playerParameters = new HashSet<>();

    public Parameter() {
    }

    public Parameter(String name, String description) {
        this.name = name;
        this.description = description;
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

    public Set<Position> getPositions() {
        Set<Position> set = new HashSet<>();
        positionParameters.forEach(x -> set.add(x.getPosition()));
        return set;
    }

    public Set<Player> getPlayers() {
        Set<Player> set = new HashSet<>();
        playerParameters.forEach(x -> set.add(x.getPlayer()));
        return set;
    }

    public void updateData(UpdateParameterDTO updateParameterDTO) {
        name = updateParameterDTO.getName();
        description = updateParameterDTO.getDescription();
    }

    @Override
    public int compareTo(Parameter other) {
        return name.compareTo(other.getName());
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
        Parameter other = (Parameter) obj;
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