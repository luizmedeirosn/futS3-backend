package com.luizmedeirosn.futs3.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.luizmedeirosn.futs3.dto.input.post.PostParameterDTO;
import com.luizmedeirosn.futs3.dto.input.update.UpdateParameterDTO;
import com.luizmedeirosn.futs3.dto.output.ParameterDTO;
import com.luizmedeirosn.futs3.entities.Parameter;
import com.luizmedeirosn.futs3.projections.PlayerParameterProjection;
import com.luizmedeirosn.futs3.repositories.ParameterRepository;

@Service
public class ParameterSerivce {

    @Autowired
    private ParameterRepository parameterRepository;

    public List<ParameterDTO> findAll() {
        return parameterRepository
            .findAll(Sort.by("name"))
            .stream()
            .map( x -> new ParameterDTO(x) )
            .toList();
    }

    public ParameterDTO findById(Long id) {
        return new ParameterDTO(parameterRepository.findById(id).get());
    }

    public List<PlayerParameterProjection> findByPlayerId(Long id) {
        return parameterRepository.findByPlayerId(id);
    }

    public ParameterDTO save(PostParameterDTO parameterInputDTO) {
        Parameter parameter = new Parameter();
        parameter.setName(parameterInputDTO.getName());
        parameter.setDescription(parameterInputDTO.getDescription());
        parameter = parameterRepository.save(parameter);
        return new ParameterDTO(parameter);
    }

    public ParameterDTO update(Long id, UpdateParameterDTO updateParameterDTO) {
        Parameter parameter = parameterRepository.getReferenceById(id);
        parameter.updateData(updateParameterDTO);
        parameter = parameterRepository.save(parameter);
        return new ParameterDTO(parameter);
    }

    public void deleteById(Long id) {
        parameterRepository.deleteById(id);
    }

}