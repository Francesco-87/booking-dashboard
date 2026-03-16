package com.ciccone.backend.dto;

import jakarta.validation.constraints.*;

public class StaffProfileRequestDto {

    @NotNull
    private Long userId;    
    @NotBlank
    private String displayName;
    private Boolean isActive;

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    
    
}
