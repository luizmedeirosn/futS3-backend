package com.luizmedeirosn.futs3.entities;

import com.luizmedeirosn.futs3.shared.dto.request.ParameterRequestDTO;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_parameter")
public class Parameter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "id.parameter", fetch = FetchType.LAZY)
    private Set<PositionParameter> positionParameters;

    @OneToMany(mappedBy = "id.parameter", fetch = FetchType.LAZY)
    private final Set<PlayerParameter> playerParameters = new HashSet<>();

    public Parameter() {
    }

    public Parameter(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void updateData(ParameterRequestDTO parameterRequestDTO) {
        name = parameterRequestDTO.name();
        description = parameterRequestDTO.description();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public void setName(@Nonnull String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<PositionParameter> getPositionParameters() {
        return positionParameters;
    }

    public Set<PlayerParameter> getPlayerParameters() {
        return playerParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parameter parameter)) return false;
        return Objects.equals(id, parameter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}