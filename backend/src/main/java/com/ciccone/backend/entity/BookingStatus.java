package com.ciccone.backend.entity;

// Enum defining the possible states of a booking
public enum BookingStatus {
    // Booking has been requested but not yet confirmed by staff
    REQUESTED,
    // Booking has been confirmed by staff and is scheduled
    CONFIRMED,
    // Booking has been cancelled and will not take place
    CANCELLED,
    // Booking has been completed successfully
    COMPLETED
}