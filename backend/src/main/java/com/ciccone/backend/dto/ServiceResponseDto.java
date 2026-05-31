package com.ciccone.backend.dto;

import java.time.OffsetDateTime;

// DTO for service data returned in API responses; uses immutable final fields
public class ServiceResponseDto {

    // Service's unique identifier from the database
    private final Long id;
    // Service name
    private final String name;
    // Service description with additional details
    private final String description;
    // Duration of service in minutes
    private final Integer durationMinutes;
    // Price of service in cents
    private final Integer priceCents;
    // Flag indicating if service is available
    private final Boolean isActive;
    // Timestamp when service record was created
    private final OffsetDateTime createdAt;
    // Timestamp when service record was last updated
    private final OffsetDateTime updatedAt;

    // All-args constructor to initialize immutable response DTO with all fields
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

    // Getter methods for all immutable fields
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public Integer getPriceCents() { return priceCents; }
    public Boolean getIsActive() { return isActive; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
}