package com.ciccone.backend.dto;

import org.springframework.stereotype.Component;
import com.ciccone.backend.entity.StaffProfileEntity;

// Component that converts between StaffProfileEntity (database) and staff profile DTOs (API layer)
@Component
public class StaffProfileMapper {

    // Converts StaffProfileRequestDto to StaffProfileEntity for persistence to database
    public StaffProfileEntity toEntity(StaffProfileRequestDto dto) {
        // Return null if input is null to prevent null pointer exceptions
        if (dto == null) {
            return null;
        }

        // Create new entity and map all DTO fields to corresponding entity fields
        StaffProfileEntity entity = new StaffProfileEntity();
        entity.setUserId(dto.getUserId());
        entity.setDisplayName(dto.getDisplayName());

        // Set isActive only if it was provided (not null) to allow default values
        if (dto.getIsActive() != null) {
            entity.setIsActive(dto.getIsActive());
        }

        return entity;
    }

    // Converts StaffProfileEntity from database to StaffProfileResponseDto for API response
    public StaffProfileResponseDto toResponseDto(StaffProfileEntity entity) {
        // Return null if entity is null to prevent null pointer exceptions
        if (entity == null) {
            return null;
        }

        // Create response DTO with all entity fields, including audit timestamps
        return new StaffProfileResponseDto(
                entity.getId(),
                entity.getUserId(),
                entity.getDisplayName(),
                entity.getIsActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}