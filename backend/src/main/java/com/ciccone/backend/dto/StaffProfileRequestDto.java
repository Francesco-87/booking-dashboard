package com.ciccone.backend.dto;

import jakarta.validation.constraints.*;

// DTO for staff profile creation and update requests
public class StaffProfileRequestDto {

    // Reference to the user account associated with this staff profile; required field
    @NotNull
    private Long userId;    
    
    // Display name for the staff member; must not be blank
    @NotBlank
    private String displayName;
    
    // Flag indicating if staff member is available/active; optional with default value
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