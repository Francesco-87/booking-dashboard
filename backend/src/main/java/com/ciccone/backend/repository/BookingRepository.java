package com.ciccone.backend.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ciccone.backend.entity.BookingEntity;

// Spring Data JPA repository for BookingEntity
// Provides built-in CRUD operations (Create, Read, Update, Delete) for booking database operations
// Also includes custom query methods for checking booking conflicts and availability
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    
    // Checks if a staff member has any existing bookings that overlap with the given time range
    // Used for checking schedule conflicts before creating a new booking
    // Returns true if there's a conflict, false if the staff member is available
    boolean existsByStaffProfileIdAndStartTimeLessThanAndEndTimeGreaterThan(
        Long staffProfileId,
        OffsetDateTime endTime,
        OffsetDateTime startTime
    );
    
    // Checks if a staff member has any existing bookings that overlap with the given time range, excluding a specific booking
    // Used when updating a booking to check for conflicts without counting the booking being updated
    // Returns true if there's a conflict with other bookings, false if the staff member is available
    boolean existsByStaffProfileIdAndStartTimeLessThanAndEndTimeGreaterThanAndIdNot(
        Long staffProfileId,
        OffsetDateTime endTime,
        OffsetDateTime startTime,
        Long bookingId    
    );
}