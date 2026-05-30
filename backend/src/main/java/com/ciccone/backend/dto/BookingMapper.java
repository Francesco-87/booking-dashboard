package com.ciccone.backend.dto;

import org.springframework.stereotype.Component;

import com.ciccone.backend.entity.BookingEntity;   


// Component that converts between BookingEntity (database) and booking DTOs (API layer)
@Component
public class BookingMapper {

    // Converts BookingRequestDto to BookingEntity for persistence to database
    public BookingEntity toEntity(BookingRequestDto dto) {
        // Return null if input is null to prevent null pointer exceptions
        if (dto == null) {
            return null;
        }

        // Create new entity and map all DTO fields to corresponding entity fields
        BookingEntity entity = new BookingEntity();
        entity.setServiceId(dto.getServiceId());
        entity.setStaffProfileId(dto.getStaffProfileId());
        entity.setCreatedByUserId(dto.getCreatedByUserId());
        entity.setCustomerUserId(dto.getCustomerUserId());
        entity.setCustomerName(dto.getCustomerName());
        entity.setCustomerEmail(dto.getCustomerEmail());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setNotes(dto.getNotes());

        return entity;
    }

    // Converts BookingEntity from database to BookingResponseDto for API response
    public BookingResponseDto toResponseDto(BookingEntity entity) {
        // Return null if entity is null to prevent null pointer exceptions
        if (entity == null) {
            return null;
        }

        // Create response DTO with all entity fields, including audit timestamps and status
        return new BookingResponseDto(
                entity.getId(),
                entity.getServiceId(),
                entity.getStaffProfileId(),
                entity.getCreatedByUserId(),
                entity.getCustomerUserId(),
                entity.getCustomerName(),
                entity.getCustomerEmail(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getStatus(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}