package com.luizmedeirosn.futs3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizmedeirosn.futs3.entities.Parameter;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {
}
