package com.ciccone.backend.dto;

import org.springframework.stereotype.Component;

import com.ciccone.backend.entity.ServiceEntity;


@Component
public class ServiceMapper {

 public ServiceEntity toEntity(ServiceRequestDto dto) {
        if (dto == null) {
            return null;
        }
        ServiceEntity entity = new ServiceEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDurationMinutes(dto.getDurationMinutes());
        entity.setPriceCents(dto.getPriceCents());
        entity.setIsActive(dto.getIsActive());
        return entity;
                
    }

    public ServiceResponseDto toResponseDto(ServiceEntity entity){
        if (entity == null) {
            return null;
        }
        ServiceResponseDto dto = new ServiceResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setDurationMinutes(entity.getDurationMinutes());
        dto.setPriceCents(entity.getPriceCents());
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    
    
}
