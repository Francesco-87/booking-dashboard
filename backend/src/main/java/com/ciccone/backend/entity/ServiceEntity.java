package com.ciccone.backend.entity;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.*;


import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

// JPA entity representing a service offered in the booking system
@Entity
@Table(name = "services")
public class ServiceEntity {

    // Primary key; auto-generated unique identifier for each service
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Service name; required field that must not be blank
    @NotBlank
    private String name;

    // Service description providing additional details; optional field
    private String description;

    // Duration of the service in minutes; must be a positive number (at least 1)
    @Column(name = "duration_minutes")
    @Positive
    private Integer durationMinutes;

    // Price of the service in cents to avoid floating-point precision issues; must be zero or positive
    @Column(name = "price_cents")
    @PositiveOrZero
    private Integer priceCents;

    // Flag indicating if service is available for booking; defaults to true
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    // Timestamp when service record was created; managed by database
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    // Timestamp when service record was last updated; managed by database
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    // Default no-arg constructor required by JPA
    public ServiceEntity() {
    }

    // Getters and setters for all fields
    public Long getId() {
        return id;
    }

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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}