package com.luizmedeirosn.futs3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luizmedeirosn.futs3.entities.PositionParameter;
import com.luizmedeirosn.futs3.repositories.PositionParameterRepository;

@Service
public class PositionParameterService {
    
    @Autowired
    PositionParameterRepository repository;

    public PositionParameter save(PositionParameter posParam) {
        posParam = repository.save(posParam);
        return posParam;
    }
}
