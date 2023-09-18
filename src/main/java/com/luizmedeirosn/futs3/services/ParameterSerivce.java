package com.luizmedeirosn.futs3.services;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luizmedeirosn.futs3.dto.ParameterMinDTO;
import com.luizmedeirosn.futs3.entities.Parameter;
import com.luizmedeirosn.futs3.repositories.ParameterRepository;

@Service
public class ParameterSerivce {

    @Autowired
    private ParameterRepository repository;

    public Set<Parameter> findAll() {
        Set<Parameter> set = new TreeSet<>(repository.findAll());
        return set;
    }

    public Parameter findById(Long id) {
        Optional<Parameter> entity = repository.findById(id);
        return entity.get();
    }

    public Parameter save(Parameter entity) {
        entity = repository.save(entity);
        return entity;
    }

    public Parameter update(Long id, ParameterMinDTO obj) {
        Parameter entity = repository.getReferenceById(id);
        entity.updateData(obj);
        entity = repository.save(entity);
        return entity;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}