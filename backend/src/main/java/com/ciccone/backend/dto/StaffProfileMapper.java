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
        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        return entity;
    }

    public StaffProfileResponseDto toResponseDto(StaffProfileEntity entity) {
        if (entity == null) {
            return null;
        }
        StaffProfileResponseDto dto = new StaffProfileResponseDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setDisplayName(entity.getDisplayName());
        dto.setIsActive(entity.getIsActive() );
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
    
}
