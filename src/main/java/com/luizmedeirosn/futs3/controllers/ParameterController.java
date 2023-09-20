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

import com.luizmedeirosn.futs3.dto.input.post.PostParameterDTO;
import com.luizmedeirosn.futs3.dto.input.update.UpdateParameterDTO;
import com.luizmedeirosn.futs3.dto.output.ParameterDTO;
import com.luizmedeirosn.futs3.services.ParameterSerivce;

@RestController
@RequestMapping(value = "/parameters")
public class ParameterController {
    
    @Autowired
    private ParameterSerivce parameterSerivce;

    @GetMapping
    public ResponseEntity<Set<ParameterDTO>> findAll() {
        Set<ParameterDTO> set = parameterSerivce.findAll();
        ResponseEntity<Set<ParameterDTO>> response = ResponseEntity.ok().body(set);
        return response;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ParameterDTO> findById(@PathVariable Long id) {
        ParameterDTO parameterDTO = parameterSerivce.findById(id);
        ResponseEntity<ParameterDTO> response = ResponseEntity.ok().body(parameterDTO);
        return response;
    }

    @PostMapping
    public ResponseEntity<ParameterDTO> save(@RequestBody PostParameterDTO postParameterDTO) {
        ParameterDTO parameterDTO = parameterSerivce.save(postParameterDTO);
        URI uri = 
            ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(parameterDTO.getId())
            .toUri();
        ResponseEntity<ParameterDTO> response = ResponseEntity.created(uri).body(parameterDTO);
        return response;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ParameterDTO> updateById(@PathVariable Long id, @RequestBody UpdateParameterDTO updateParameterDTO) {
        ParameterDTO parameterDTO = parameterSerivce.update(id, updateParameterDTO);
        ResponseEntity<ParameterDTO> response = ResponseEntity.ok().body(parameterDTO);
        return response;
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        parameterSerivce.deleteById(id);
        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        return response;
    }

}