package com.ciccone.backend.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ciccone.backend.dto.BookingMapper;
import com.ciccone.backend.dto.BookingRequestDto;
import com.ciccone.backend.dto.BookingResponseDto;
import com.ciccone.backend.entity.BookingEntity;
import com.ciccone.backend.entity.BookingStatus;
import com.ciccone.backend.exception.ConflictException;
import com.ciccone.backend.exception.BadRequestException;
import com.ciccone.backend.exception.ResourceNotFoundException;
import com.ciccone.backend.repository.BookingRepository;
import com.ciccone.backend.repository.StaffProfileRepository;
import com.ciccone.backend.repository.UserRepository;
import com.ciccone.backend.repository.ServiceRepository;


@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final StaffProfileRepository staffProfileRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;

    public BookingService(
        BookingRepository bookingRepository,
        BookingMapper bookingMapper,
        StaffProfileRepository staffProfileRepository,
        ServiceRepository serviceRepository,
        UserRepository userRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.staffProfileRepository = staffProfileRepository;
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
    }

 // Create booking - validate input, check for conflicts, and return created booking
public BookingResponseDto createBooking(BookingRequestDto bookingRequestDto) {
    validateTime(bookingRequestDto.getStartTime(), bookingRequestDto.getEndTime());
    validateStaffAndService(bookingRequestDto.getStaffProfileId(), bookingRequestDto.getServiceId());
    validateUserReferences(bookingRequestDto);
    validateCustomer(bookingRequestDto);
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
    validateUserReferences(updatedBooking);
    validateCustomer(updatedBooking);
    validateNoOverlapExcludingCurrent(
            existingBooking.getId(),
            updatedBooking.getStaffProfileId(),
            updatedBooking.getStartTime(),
            updatedBooking.getEndTime()
    );

    existingBooking.setServiceId(updatedBooking.getServiceId());
    existingBooking.setStaffProfileId(updatedBooking.getStaffProfileId());
    existingBooking.setCustomerUserId(updatedBooking.getCustomerUserId());
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


    private void validateStaffAndService(Long staffProfileId, Long serviceId) {
        staffProfileRepository.findById(staffProfileId)
            .orElseThrow(() -> new ResourceNotFoundException("Staff profile not found"));

        serviceRepository.findById(serviceId)
            .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
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

    private void validateCustomer(BookingRequestDto bookingRequestDto) {
    boolean hasCustomerUserId = bookingRequestDto.getCustomerUserId() != null;
    boolean hasCustomerName = bookingRequestDto.getCustomerName() != null
            && !bookingRequestDto.getCustomerName().trim().isEmpty();

    if (!hasCustomerUserId && !hasCustomerName) {
        throw new BadRequestException("Booking must have either a registered customer or a customer name");
    }
}

private void validateUserReferences(BookingRequestDto bookingRequestDto) {
    userRepository.findById(bookingRequestDto.getCreatedByUserId())
            .orElseThrow(() -> new ResourceNotFoundException("Created by user not found"));

    if (bookingRequestDto.getCustomerUserId() != null) {
        userRepository.findById(bookingRequestDto.getCustomerUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer user not found"));
    }
}
       
}


 
        

       