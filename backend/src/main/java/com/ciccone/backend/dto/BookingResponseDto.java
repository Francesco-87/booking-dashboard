package com.ciccone.backend.dto;

import java.time.OffsetDateTime;

import com.ciccone.backend.entity.BookingStatus;



// DTO for booking data returned in API responses; uses immutable final fields
public class BookingResponseDto {

    // Booking's unique identifier from the database
    private final Long id;
    // Reference to the booked service
    private final Long serviceId;
    // Reference to the assigned staff member
    private final Long staffProfileId;
    // Reference to the user who created the booking
    private final Long createdByUserId;
    // Reference to the customer's user account (if registered customer)
    private final Long customerUserId;
    // Name of the customer (for guest or registered customers)
    private final String customerName;
    // Email of the customer
    private final String customerEmail;
    // Start date and time of the booking appointment
    private final OffsetDateTime startTime;
    // End date and time of the booking appointment
    private final OffsetDateTime endTime;
    // Current status of the booking (CONFIRMED, CANCELLED, etc.)
    private final BookingStatus status;
    // Additional notes or special requests for the booking
    private final String notes;
    // Timestamp when booking record was created
    private final OffsetDateTime createdAt;
    // Timestamp when booking record was last updated
    private final OffsetDateTime updatedAt;

    // All-args constructor to initialize immutable response DTO with all fields
    public BookingResponseDto(
            Long id,
            Long serviceId,
            Long staffProfileId,
            Long createdByUserId,
            Long customerUserId,
            String customerName,
            String customerEmail,
            OffsetDateTime startTime,
            OffsetDateTime endTime,
            BookingStatus status,
            String notes,
            OffsetDateTime createdAt,
            OffsetDateTime updatedAt
    ) {
        this.id = id;
        this.serviceId = serviceId;
        this.staffProfileId = staffProfileId;
        this.createdByUserId = createdByUserId;
        this.customerUserId = customerUserId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.notes = notes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter methods for all immutable fields
    public Long getId() {
        return id;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public Long getStaffProfileId() {
        return staffProfileId;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public Long getCustomerUserId() {
        return customerUserId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public OffsetDateTime getStartTime() {
        return startTime;
    }

    public OffsetDateTime getEndTime() {
        return endTime;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}