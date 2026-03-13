package com.ciccone.backend.dto;

import java.time.OffsetDateTime;

import com.ciccone.backend.entity.BookingStatus;



public class BookingResponseDto {

    private Long id;
    private Long serviceId;
    private Long staffProfileId;
    private String customerName;
    private String customerEmail;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private BookingStatus status;
    private String notes;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceId() {
        return serviceId;
    }
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
    public Long getStaffProfileId() {
        return staffProfileId;
    }
    public void setStaffProfileId(Long staffProfileId) {
        this.staffProfileId = staffProfileId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerEmail() {
        return customerEmail;
    }
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    public OffsetDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(OffsetDateTime startTime) {
        this.startTime = startTime;
    }
    public OffsetDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(OffsetDateTime endTime) {
        this.endTime = endTime;
    }
    public BookingStatus getStatus() {
        return status;
    }
    public void setStatus(BookingStatus status) {
        this.status = status;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }    
    
}
