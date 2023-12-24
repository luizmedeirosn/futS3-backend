package com.luizmedeirosn.futs3.services;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.luizmedeirosn.futs3.entities.PlayerPicture;
import com.luizmedeirosn.futs3.repositories.PlayerPictureRepository;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlayerPictureService {

    private final PlayerPictureRepository playerPictureRepository;

    public static String createPictureLink(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/playerspictures/" + id).replaceQuery(null)
                .toUriString();
    }

    public PlayerPicture findById(Long id) {
        return playerPictureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Image ID not found"));
    }

}
