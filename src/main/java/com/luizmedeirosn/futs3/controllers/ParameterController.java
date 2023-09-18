package com.luizmedeirosn.futs3.controllers;

import java.net.URI;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.luizmedeirosn.futs3.dto.ParameterMinDTO;
import com.luizmedeirosn.futs3.entities.Parameter;
import com.luizmedeirosn.futs3.services.ParameterSerivce;

@RestController
@RequestMapping(value = "/parameters")
public class ParameterController {
    
    @Autowired
    private ParameterSerivce service;

    @GetMapping
    public ResponseEntity<Set<Parameter>> findAll() {
        Set<Parameter> set = service.findAll();
        ResponseEntity<Set<Parameter>> response = ResponseEntity.ok().body(set);
        return response;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Parameter> findById(@PathVariable Long id) {
        Parameter entity = service.findById(id);
        ResponseEntity<Parameter> response = ResponseEntity.ok().body(entity);
        return response;
    }

    @PostMapping
    public ResponseEntity<Parameter> save(@RequestBody Parameter entity) {
        entity = service.save(entity);
        URI uri = 
            ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(entity.getId())
            .toUri();
        ResponseEntity<Parameter> response = ResponseEntity.created(uri).body(entity);
        return response;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Parameter> updateById(@PathVariable Long id, @RequestBody ParameterMinDTO obj) {
        Parameter entity = service.update(id, obj);
        ResponseEntity<Parameter> response = ResponseEntity.ok().body(entity);
        return response;
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        return response;
    }

}
