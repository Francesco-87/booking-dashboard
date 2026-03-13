package com.ciccone.backend.dto;

import org.springframework.stereotype.Component;

import com.ciccone.backend.entity.BookingEntity;   


@Component
public class BookingMapper {

    public BookingEntity toEntity(BookingRequestDto dto) {
        if (dto == null) {
            return null;
        }
        BookingEntity entity = new BookingEntity();
        entity.setServiceId(dto.getServiceId());
        entity.setStaffProfileId(dto.getStaffProfileId());
        entity.setCustomerName(dto.getCustomerName());
        entity.setCustomerEmail(dto.getCustomerEmail());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setNotes(dto.getNotes());
        return entity;
    }

    public BookingResponseDto toResponseDto(BookingEntity entity) {
        if (entity == null) {
            return null;
        }
        BookingResponseDto dto = new BookingResponseDto();
        dto.setId(entity.getId());
        dto.setServiceId(entity.getServiceId());
        dto.setStaffProfileId(entity.getStaffProfileId());
        dto.setCustomerName(entity.getCustomerName());
        dto.setCustomerEmail(entity.getCustomerEmail());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setStatus(entity.getStatus());
        dto.setNotes(entity.getNotes());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
    
}
