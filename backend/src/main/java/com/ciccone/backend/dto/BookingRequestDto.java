package com.ciccone.backend.dto;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.*;

// DTO for booking creation and update requests
public class BookingRequestDto {

    // Reference to the service being booked; required field
    @NotNull
    private Long serviceId;

    // Reference to the staff member assigned to the booking; required field
    @NotNull
    private Long staffProfileId;

    // Reference to the user who created the booking (admin); required field
    @NotNull
    private Long createdByUserId;

    // Reference to the customer's user account; optional if booking is for guest customer
    private Long customerUserId;

    // Name of the customer; used when customerUserId is not provided (guest booking)
    private String customerName;

    // Email of the customer; must be valid email format if provided
    @Email
    private String customerEmail;

    // Start date and time of the booking appointment; required field
    @NotNull
    private OffsetDateTime startTime;

    // End date and time of the booking appointment; required field
    @NotNull
    private OffsetDateTime endTime;

    // Additional notes or special requests for the booking; optional field
    private String notes;

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

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Long getCustomerUserId() {
        return customerUserId;
    }

    public void setCustomerUserId(Long customerUserId) {
        this.customerUserId = customerUserId;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}