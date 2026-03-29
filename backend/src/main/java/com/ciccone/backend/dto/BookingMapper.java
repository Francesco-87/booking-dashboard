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

        return new BookingResponseDto(
                entity.getId(),
                entity.getServiceId(),
                entity.getStaffProfileId(),
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
