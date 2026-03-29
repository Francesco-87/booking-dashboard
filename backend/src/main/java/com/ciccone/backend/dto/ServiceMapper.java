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

        if (dto.getIsActive() != null) {
            entity.setIsActive(dto.getIsActive());
        }

        return entity;
    }

    public ServiceResponseDto toResponseDto(ServiceEntity entity) {
        if (entity == null) {
            return null;
        }

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
