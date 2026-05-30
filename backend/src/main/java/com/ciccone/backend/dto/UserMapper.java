package com.ciccone.backend.dto;

import org.springframework.stereotype.Component;

import com.ciccone.backend.entity.UserEntity;

// Component that converts between UserEntity (database) and user DTOs (API layer)
@Component
public class UserMapper {

    // Converts UserCreateRequestDto to UserEntity for persistence to database
    public UserEntity toEntity(UserCreateRequestDto dto) {
        // Return null if input is null to prevent null pointer exceptions
        if (dto == null) {
            return null;
        }

        // Create new entity and map all DTO fields to corresponding entity fields
        UserEntity entity = new UserEntity();
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
        entity.setPasswordHash(dto.getPasswordHash());
        entity.setRole(dto.getRole());

        // Set isActive only if it was provided (not null) to allow default values
        if (dto.getIsActive() != null) {
            entity.setIsActive(dto.getIsActive());
        }

        return entity;
    }

    // Converts UserEntity from database to UserResponseDto for API response
    public UserResponseDto toResponseDto(UserEntity entity) {
        // Return null if entity is null to prevent null pointer exceptions
        if (entity == null) {
            return null;
        }

        // Create response DTO with all entity fields, including audit timestamps
        return new UserResponseDto(
                entity.getId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getRole(),
                entity.getIsActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}