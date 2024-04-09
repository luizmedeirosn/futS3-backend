package com.luizmedeirosn.futs3.services;

import com.luizmedeirosn.futs3.entities.CustomUser;
import com.luizmedeirosn.futs3.repositories.CustomUserRepository;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CustomUserService {

    private final CustomUserRepository customUserRepository;

    public CustomUserService(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<CustomUser> findAll() {
        return customUserRepository.findAll(Sort.by("username"));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CustomUser findById(@NonNull Long id) {
        return customUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found. ID: " + id));
    }
}
