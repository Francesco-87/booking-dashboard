package com.ciccone.backend.dto;

import org.springframework.stereotype.Component;

import com.ciccone.backend.entity.UserEntity;

@Component
public class UserMapper {

    public UserEntity toEntity(UserRequestDto dto) {
        if (dto == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
        entity.setPasswordHash(dto.getPasswordHash());
        entity.setRole(dto.getRole());
        if (dto.getIsActive() != null) { entity.setIsActive(dto.getIsActive());}
        return entity;
    }

    public UserResponseDto toResponseDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        UserResponseDto dto = new UserResponseDto();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        dto.setIsActive(entity.getIsActive() );
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
    
}
