package com.ciccone.backend.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ciccone.backend.entity.BookingEntity;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    

    boolean existsByStaffProfileIdAndStartTimeLessThanAndEndTimeGreaterThan(
    Long staffProfileId,
    OffsetDateTime endTime,
    OffsetDateTime startTime
);
 boolean existsByStaffProfileIdAndStartTimeLessThanAndEndTimeGreaterThanAndIdNot(
    Long staffProfileId,
    OffsetDateTime endTime,
    OffsetDateTime startTime,
    Long bookingId    
);
}
