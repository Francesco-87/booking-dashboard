package com.ciccone.backend.dto;

import org.springframework.stereotype.Component;

import com.ciccone.backend.entity.ServiceEntity;


// Component that converts between ServiceEntity (database) and service DTOs (API layer)
@Component
public class ServiceMapper {

    // Converts ServiceRequestDto to ServiceEntity for persistence to database
    public ServiceEntity toEntity(ServiceRequestDto dto) {
        // Return null if input is null to prevent null pointer exceptions
        if (dto == null) {
            return null;
        }

        // Create new entity and map all DTO fields to corresponding entity fields
        ServiceEntity entity = new ServiceEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDurationMinutes(dto.getDurationMinutes());
        entity.setPriceCents(dto.getPriceCents());

        // Set isActive only if it was provided (not null) to allow default values
        if (dto.getIsActive() != null) {
            entity.setIsActive(dto.getIsActive());
        }

        return entity;
    }

    // Converts ServiceEntity from database to ServiceResponseDto for API response
    public ServiceResponseDto toResponseDto(ServiceEntity entity) {
        // Return null if entity is null to prevent null pointer exceptions
        if (entity == null) {
            return null;
        }

        // Create response DTO with all entity fields, including audit timestamps
        return new ServiceResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getDurationMinutes(),
                entity.getPriceCents(),
                entity.getIsActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}