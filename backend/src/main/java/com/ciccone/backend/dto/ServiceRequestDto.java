package com.ciccone.backend.dto;

import jakarta.validation.constraints.*;

// DTO for service creation and update requests
public class ServiceRequestDto {      
   
    // Service name; must not be blank
    @NotBlank
    private String name;
    
    // Service description; optional field for additional details
    private String description;
    
    // Duration of service in minutes; must be a positive number (at least 1)
    @NotNull
    @Positive
    private Integer durationMinutes;
    
    // Price of service in cents to avoid floating-point issues; must be zero or positive
    @NotNull
    @PositiveOrZero
    private Integer priceCents;
    
    // Flag indicating if service is available; optional with default value
    private Boolean isActive;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Integer getPriceCents() {
        return priceCents;
    }

    public void setPriceCents(Integer priceCents) {
        this.priceCents = priceCents;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
}