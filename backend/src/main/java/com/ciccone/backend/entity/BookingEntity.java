package com.ciccone.backend.entity;


import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;


// JPA entity representing a booking (appointment) in the system
@Entity
@Table(name = "bookings")
public class BookingEntity {

    // Primary key; auto-generated unique identifier for each booking
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Foreign key reference to the service being booked; required field
    @Column(name = "service_id")
    @NotNull
    private Long serviceId;

    // Foreign key reference to the staff member assigned to this booking; required field
    @Column(name = "staff_profile_id")
    @NotNull
    private Long staffProfileId;

    // Foreign key reference to the user (usually admin) who created the booking; required field
    @Column(name = "created_by_user_id")
    @NotNull
    private Long createdByUserId;

    // Foreign key reference to the customer's user account; optional for guest customers
    @Column(name = "customer_user_id")
    private Long customerUserId;

    // Name of the customer; used when customer is not registered in system
    @Column(name = "customer_name")
    private String customerName;

    // Email of the customer; must be valid email format if provided
    @Column(name = "customer_email")
    @Email
    private String customerEmail;

    // Start date and time of the booking appointment; required field
    @Column(name = "start_time")
    @NotNull
    private OffsetDateTime startTime;

    // End date and time of the booking appointment; required field
    @Column(name = "end_time")
    @NotNull
    private OffsetDateTime endTime;

    // Current status of the booking (REQUESTED, CONFIRMED, CANCELLED, or COMPLETED)
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    // Additional notes or special requests for this booking; optional field
    private String notes;

    // Timestamp when booking record was created; managed by database
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    // Timestamp when booking record was last updated; managed by database
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    // Default no-arg constructor required by JPA
    public BookingEntity() {
    }

    // Getters and setters for all fields
    public Long getId() {
        return id;
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