package com.ciccone.backend.dto;

import java.time.OffsetDateTime;

public class ServiceResponseDto {

    private final Long id;
    private final String name;
    private final String description;
    private final Integer durationMinutes;
    private final Integer priceCents;
    private final Boolean isActive;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;

    public ServiceResponseDto(Long id, String name, String description,
                              Integer durationMinutes, Integer priceCents,
                              Boolean isActive, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.priceCents = priceCents;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public Integer getPriceCents() { return priceCents; }
    public Boolean getIsActive() { return isActive; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
}