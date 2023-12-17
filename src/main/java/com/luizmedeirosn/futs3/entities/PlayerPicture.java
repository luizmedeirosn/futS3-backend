package com.luizmedeirosn.futs3.entities;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_player_picture")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlayerPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
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

}
