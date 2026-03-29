package com.ciccone.backend.dto;

import java.time.OffsetDateTime;

import com.ciccone.backend.entity.BookingStatus;



public class BookingResponseDto {

    private final Long id;
    private final Long serviceId;
    private final Long staffProfileId;
    private final String customerName;
    private final String customerEmail;
    private final OffsetDateTime startTime;
    private final OffsetDateTime endTime;
    private final BookingStatus status;
    private final String notes;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;

    public BookingResponseDto(Long id, Long serviceId, Long staffProfileId,
                              String customerName, String customerEmail,
                              OffsetDateTime startTime, OffsetDateTime endTime,
                              BookingStatus status, String notes,
                              OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.serviceId = serviceId;
        this.staffProfileId = staffProfileId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public Long getServiceId() { return serviceId; }
    public Long getStaffProfileId() { return staffProfileId; }
    public String getCustomerName() { return customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public OffsetDateTime getStartTime() { return startTime; }
    public OffsetDateTime getEndTime() { return endTime; }
    public BookingStatus getStatus() { return status; }
    public String getNotes() { return notes; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
}