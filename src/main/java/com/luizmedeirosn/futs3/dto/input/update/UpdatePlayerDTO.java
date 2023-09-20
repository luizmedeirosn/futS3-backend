package com.luizmedeirosn.futs3.dto.input.update;

import java.io.Serializable;

public class UpdatePlayerDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    private Long positionId;
    
    public UpdatePlayerDTO() {
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public Long getPositionId() {
        return positionId;
    }
    
}
