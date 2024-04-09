package com.luizmedeirosn.futs3.services;

import com.luizmedeirosn.futs3.entities.PlayerPicture;
import com.luizmedeirosn.futs3.repositories.PlayerPictureRepository;
import com.luizmedeirosn.futs3.shared.exceptions.EntityNotFoundException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class PlayerPictureService {

    private final PlayerPictureRepository playerPictureRepository;

    public PlayerPictureService(PlayerPictureRepository playerPictureRepository) {
        this.playerPictureRepository = playerPictureRepository;
    }

    public static String createPictureUrl(Long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/playerspictures/" + id).replaceQuery(null)
                .toUriString();
    }

    public PlayerPicture findById(@NonNull Long id) {
        return playerPictureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Image ID not found"));
    }
}
