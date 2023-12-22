package com.luizmedeirosn.futs3.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.luizmedeirosn.futs3.shared.dto.request.update.UpdateParameterDTO;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_parameter")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Parameter implements Serializable {

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
    private Set<PositionParameter> positionParameters;

    @OneToMany(mappedBy = "id.parameter")
    private Set<PlayerParameter> playerParameters = new HashSet<>();

    public Parameter(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void updateData(UpdateParameterDTO updateParameterDTO) {
        name = updateParameterDTO.getName();
        description = updateParameterDTO.getDescription();
    }

}