package com.luizmedeirosn.futs3.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luizmedeirosn.futs3.dto.GameModeMinDTO;
import com.luizmedeirosn.futs3.entities.GameMode;
import com.luizmedeirosn.futs3.repositories.GameModeRepository;

@Service
public class GameModeService {

    @Autowired
    private GameModeRepository repository;

    public Set<GameMode> findAll() {
        Set<GameMode> set = new HashSet<>(repository.findAll());
        return set;
    }

    public GameMode findById(Long id) {
        Optional<GameMode> obj = repository.findById(id);
        return obj.get();
    }

    public GameMode save(GameMode obj) {
        obj = repository.save(obj);
        return obj;
    }

    public GameMode update(Long id, GameModeMinDTO obj) {
        GameMode entity = repository.getReferenceById(id);
        entity.updateData(obj);
        entity = repository.save(entity);
        return entity;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}