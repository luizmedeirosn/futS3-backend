package com.luizmedeirosn.futs3.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.luizmedeirosn.futs3.entities.PlayerPicture;
import com.luizmedeirosn.futs3.repositories.PlayerPictureRepository;
import com.luizmedeirosn.futs3.services.exceptions.DatabaseException;
import com.luizmedeirosn.futs3.services.exceptions.EntityNotFoundException;

@Service
public class PlayerPictureService {
    
    @Autowired
    private PlayerPictureRepository playerPictureRepository;

    public PlayerPicture findById(Long id) {
        return playerPictureRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Image ID not found"));
    }

    public PlayerPicture save(MultipartFile file) {
     
        PlayerPicture playerPicture = new PlayerPicture();
        playerPicture.setFilename(file.getOriginalFilename());
        playerPicture.setContentType(file.getContentType());
        try {
            playerPicture.setContent(file.getBytes());
        } catch (IOException e) {
            throw new DatabaseException("Error in picture reading");
        }

        System.out.println("aqui");
        return playerPictureRepository.save(playerPicture);
    }

}
