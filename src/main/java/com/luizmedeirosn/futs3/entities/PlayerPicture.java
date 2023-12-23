package com.luizmedeirosn.futs3.entities;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.luizmedeirosn.futs3.shared.exceptions.DatabaseException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_player_picture")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class PlayerPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    @Column(name = "content_type")
    private String contentType;

    @Lob
    private byte[] content;

    @OneToOne
    @MapsId
    private Player player;

    public PlayerPicture(String filename, String contentType, byte[] content, Player player) {
        this.filename = filename;
        this.contentType = contentType;
        this.content = content;
        this.player = player;
    }

    public PlayerPicture(Player player, MultipartFile playerPicture) {
        try {
            if (playerPicture != null) {
                filename = playerPicture.getOriginalFilename();
                contentType = playerPicture.getContentType();
                content = playerPicture.getBytes();
            }
            this.player = player;

        } catch (IOException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void updateData(MultipartFile playerPicture) {
        try {
            filename = playerPicture.getOriginalFilename();
            contentType = playerPicture.getContentType();
            content = playerPicture.getBytes();
        } catch (IOException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

}
