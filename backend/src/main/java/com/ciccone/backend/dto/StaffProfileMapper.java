package com.ciccone.backend.dto;

import org.springframework.stereotype.Component;
import com.ciccone.backend.entity.StaffProfileEntity;

@Component
public class StaffProfileMapper {

    public StaffProfileEntity toEntity(StaffProfileRequestDto dto) {
        if (dto == null) {
            return null;
        }

        StaffProfileEntity entity = new StaffProfileEntity();
        entity.setUserId(dto.getUserId());
        entity.setDisplayName(dto.getDisplayName());

        if (dto.getIsActive() != null) {
            entity.setIsActive(dto.getIsActive());
        }

        return entity;
    }

    public StaffProfileResponseDto toResponseDto(StaffProfileEntity entity) {
        if (entity == null) {
            return null;
        }

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
