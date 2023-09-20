package com.luizmedeirosn.futs3.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luizmedeirosn.futs3.dto.input.post.PostParameterDTO;
import com.luizmedeirosn.futs3.dto.input.update.UpdateParameterDTO;
import com.luizmedeirosn.futs3.dto.output.ParameterDTO;
import com.luizmedeirosn.futs3.entities.Parameter;
import com.luizmedeirosn.futs3.projections.ParameterProjection;
import com.luizmedeirosn.futs3.repositories.ParameterRepository;

@Service
public class ParameterSerivce {

    @Autowired
    private ParameterRepository repository;

    public Set<ParameterDTO> findAll() {
        Set<ParameterDTO> parametersDTO = new TreeSet<>();
        repository.findAll().forEach(
            obj -> parametersDTO.add(new ParameterDTO(obj) )
        );
        return parametersDTO;
    }

    public ParameterDTO findById(Long id) {
        Optional<Parameter> optionalParameter = repository.findById(id);
        ParameterDTO parameterDTO = new ParameterDTO(optionalParameter.get());
        return parameterDTO;
    }

    public List<ParameterProjection> findByPlayerId(Long id) {
        List<ParameterProjection> parametersByPlayer = repository.findByPlayerId(id);
        return parametersByPlayer;
    }

    public ParameterDTO save(PostParameterDTO parameterInputDTO) {
        Parameter parameter = new Parameter();
        parameter.setName(parameterInputDTO.getName());
        parameter.setDescription(parameterInputDTO.getDescription());
        parameter = repository.save(parameter);
        ParameterDTO parameterDTO = new ParameterDTO(parameter);
        return parameterDTO;
    }

    public ParameterDTO update(Long id, UpdateParameterDTO updateParameterDTO) {
        Parameter parameter = repository.getReferenceById(id);
        parameter.updateData(updateParameterDTO);
        parameter = repository.save(parameter);
        ParameterDTO parameterDTO = new ParameterDTO(parameter);
        return parameterDTO;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}