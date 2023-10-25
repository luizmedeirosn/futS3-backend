package com.luizmedeirosn.futs3.dto.response;

public class PlayerPictureDTO {
    
    private String filename;
    private String link;
    
    public PlayerPictureDTO() {
    }
    
    public PlayerPictureDTO(String filename, String link) {
        this.filename = filename;
        this.link = link;
    }
    
    public String getFilename() {
        return filename;
    }
    
    public String getLink() {
        return link;
    }

}
