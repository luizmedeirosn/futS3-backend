package com.luizmedeirosn.futs3.services;

import com.luizmedeirosn.futs3.repositories.CustomUserRepository;
import com.luizmedeirosn.futs3.shared.dto.response.CustomUserDTO;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;
import com.luizmedeirosn.futs3.shared.exceptions.PageableException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CustomUserService {

    private final CustomUserRepository customUserRepository;

    public CustomUserService(CustomUserRepository customUserRepository) {
        this.customUserRepository = customUserRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Page<CustomUserDTO> findAll(Pageable pageable) {
        if (pageable.getPageSize() > 30) {
            throw new PageableException("The maximum allowed size for the page: 30");
        }

        return customUserRepository.findAll(pageable).map(CustomUserDTO::new);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CustomUserDTO findById(@NonNull Long id) {
        var user = customUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User ID not found: " + id));
        return new CustomUserDTO(user);
    }
}
