package com.ciccone.backend.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ciccone.backend.dto.BookingMapper;
import com.ciccone.backend.dto.BookingRequestDto;
import com.ciccone.backend.dto.BookingResponseDto;
import com.ciccone.backend.entity.BookingEntity;
import com.ciccone.backend.entity.BookingStatus;
import com.ciccone.backend.entity.ServiceEntity;
import com.ciccone.backend.entity.StaffProfileEntity;
import com.ciccone.backend.exception.ConflictException;
import com.ciccone.backend.exception.BadRequestException;
import com.ciccone.backend.exception.ResourceNotFoundException;
import com.ciccone.backend.repository.BookingRepository;
import com.ciccone.backend.repository.StaffProfileRepository;

import com.ciccone.backend.repository.ServiceRepository;


@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final StaffProfileRepository staffProfileRepository;
    private final ServiceRepository serviceRepository;

    public BookingService(BookingRepository bookingRepository, BookingMapper bookingMapper, StaffProfileRepository staffProfileRepository, ServiceRepository serviceRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.staffProfileRepository = staffProfileRepository;
        this.serviceRepository = serviceRepository;
    }

    // Create booking - validate input, check for conflicts, and return created booking
    public BookingResponseDto createBooking(BookingRequestDto bookingRequestDto) {
    validateTime(bookingRequestDto.getStartTime(), bookingRequestDto.getEndTime());
    validateStaffAndService(bookingRequestDto.getStaffProfileId(), bookingRequestDto.getServiceId());
    validateNoOverlap(
            bookingRequestDto.getStaffProfileId(),
            bookingRequestDto.getStartTime(),
            bookingRequestDto.getEndTime()
    );
    BookingEntity bookingEntity = bookingMapper.toEntity(bookingRequestDto);
    OffsetDateTime now = OffsetDateTime.now();
    bookingEntity.setCreatedAt(now);
    bookingEntity.setUpdatedAt(now);
    bookingEntity.setStatus(BookingStatus.REQUESTED);

    return bookingMapper.toResponseDto(bookingRepository.save(bookingEntity));
}
    
    // Get all bookings 
    public List<BookingResponseDto> getAllBookings() {
        return bookingRepository.findAll().stream()
            .map(bookingMapper::toResponseDto)
            .toList();
    }

    // Get booking by ID - return 404 if not found
    public BookingResponseDto getBookingById(Long id) {
    BookingEntity booking = bookingRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

    return bookingMapper.toResponseDto(booking);
    }

    // Update booking - only allow updating certain fields and validate constraints
    public BookingResponseDto updateBooking(Long id, BookingRequestDto updatedBooking) {
    BookingEntity existingBooking = bookingRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

    validateTime(updatedBooking.getStartTime(), updatedBooking.getEndTime());
    validateStaffAndService(updatedBooking.getStaffProfileId(), updatedBooking.getServiceId());
    validateNoOverlapExcludingCurrent(
            existingBooking.getId(),
            updatedBooking.getStaffProfileId(),
            updatedBooking.getStartTime(),
            updatedBooking.getEndTime()
    );

    existingBooking.setServiceId(updatedBooking.getServiceId());
    existingBooking.setStaffProfileId(updatedBooking.getStaffProfileId());
    existingBooking.setCustomerName(updatedBooking.getCustomerName());
    existingBooking.setCustomerEmail(updatedBooking.getCustomerEmail());
    existingBooking.setStartTime(updatedBooking.getStartTime());
    existingBooking.setEndTime(updatedBooking.getEndTime());
    existingBooking.setNotes(updatedBooking.getNotes());
    existingBooking.setUpdatedAt(OffsetDateTime.now());

    return bookingMapper.toResponseDto(bookingRepository.save(existingBooking));
    }

    // Delete booking - return 404 if not found
    public void deleteBooking(Long id) {
        BookingEntity booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        bookingRepository.delete(booking);
    }


    //Helper methods 
     private void validateTime(OffsetDateTime startTime, OffsetDateTime endTime) {
        OffsetDateTime now = OffsetDateTime.now();
        if (startTime.isBefore(now)) {
            throw new BadRequestException("Start time cannot be in the past");
        }
        if (!endTime.isAfter(startTime)) {
            throw new BadRequestException("End time must be after start time");
        }
     }


     private void  validateStaffAndService(Long staffProfileId, Long serviceId) {
         StaffProfileEntity staffProfile = staffProfileRepository.findById(staffProfileId)
        .orElseThrow(() -> new ResourceNotFoundException("Staff profile not found"));

        ServiceEntity service = serviceRepository.findById(serviceId)
        .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        if (!staffProfile.getServices().contains(service)) {
            throw new BadRequestException("Staff cannot perform this service");
            }
     }

    private void validateNoOverlap(Long staffProfileId, OffsetDateTime startTime, OffsetDateTime endTime) {
        if (bookingRepository.existsByStaffProfileIdAndStartTimeLessThanAndEndTimeGreaterThan(
                staffProfileId,
                endTime,
                startTime            
        )) {
            throw new ConflictException("Booking overlaps with existing booking");
        }
    }
    private void validateNoOverlapExcludingCurrent(Long bookingId, Long staffProfileId, OffsetDateTime startTime, OffsetDateTime endTime) {
        if (bookingRepository.existsByStaffProfileIdAndStartTimeLessThanAndEndTimeGreaterThanAndIdNot(
            staffProfileId,
            endTime,
            startTime,
            bookingId
                           
        )) {
            throw new ConflictException("Booking overlaps with existing booking");
        }
    }
       
}


 
        

       