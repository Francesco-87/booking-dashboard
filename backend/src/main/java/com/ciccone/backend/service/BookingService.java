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


// Service layer for booking business logic; handles CRUD operations and complex booking validations
// Manages booking creation, updates, cancellation with conflict detection and user validation
@Service
public class BookingService {

    // Dependencies injected via constructor for database access, mapping, and related entity access
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final StaffProfileRepository staffProfileRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;

    // Constructor for dependency injection
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

    // Creates a new booking with comprehensive validation and conflict checking
    public BookingResponseDto createBooking(BookingRequestDto bookingRequestDto) {
        // Validate that start time is in future and end time is after start time
        validateTime(bookingRequestDto.getStartTime(), bookingRequestDto.getEndTime());
        
        // Validate that referenced staff and service exist in database
        validateStaffAndService(bookingRequestDto.getStaffProfileId(), bookingRequestDto.getServiceId());
        
        // Validate that user references (creator and customer) are valid
        validateUserReferences(bookingRequestDto);
        
        // Validate that booking has either a registered customer or customer name
        validateCustomer(bookingRequestDto);
        
        // Check for scheduling conflicts with staff member's other bookings
        validateNoOverlap(
                bookingRequestDto.getStaffProfileId(),
                bookingRequestDto.getStartTime(),
                bookingRequestDto.getEndTime()
        );

        // Convert DTO to entity using mapper
        BookingEntity bookingEntity = bookingMapper.toEntity(bookingRequestDto);
        
        // Set creation and update timestamps to current time
        OffsetDateTime now = OffsetDateTime.now();
        bookingEntity.setCreatedAt(now);
        bookingEntity.setUpdatedAt(now);
        
        // Set initial status to REQUESTED (pending staff confirmation)
        bookingEntity.setStatus(BookingStatus.REQUESTED);

        // Save to database and return as response DTO
        return bookingMapper.toResponseDto(bookingRepository.save(bookingEntity));
    }
    
    // Retrieves all bookings from database and returns as DTOs
    public List<BookingResponseDto> getAllBookings() {
        return bookingRepository.findAll().stream()
            .map(bookingMapper::toResponseDto)
            .toList();
    }

    // Retrieves a specific booking by ID; throws exception if not found
    public BookingResponseDto getBookingById(Long id) {
        // Fetch booking or throw 404 Not Found exception
        BookingEntity booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        return bookingMapper.toResponseDto(booking);
    }

    // Updates a booking with validation and conflict checking (excluding current booking)
    public BookingResponseDto updateBooking(Long id, BookingRequestDto updatedBooking) {
        // Fetch existing booking or throw 404 Not Found exception
        BookingEntity existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        // Validate that start time is in future and end time is after start time
        validateTime(updatedBooking.getStartTime(), updatedBooking.getEndTime());
        
        // Validate that referenced staff and service exist in database
        validateStaffAndService(updatedBooking.getStaffProfileId(), updatedBooking.getServiceId());
        
        // Validate that user references (creator and customer) are valid
        validateUserReferences(updatedBooking);
        
        // Validate that booking has either a registered customer or customer name
        validateCustomer(updatedBooking);
        
        // Check for scheduling conflicts excluding the current booking being updated
        validateNoOverlapExcludingCurrent(
                existingBooking.getId(),
                updatedBooking.getStaffProfileId(),
                updatedBooking.getStartTime(),
                updatedBooking.getEndTime()
        );

        // Update booking information with new values
        existingBooking.setServiceId(updatedBooking.getServiceId());
        existingBooking.setStaffProfileId(updatedBooking.getStaffProfileId());
        existingBooking.setCustomerUserId(updatedBooking.getCustomerUserId());
        existingBooking.setCustomerName(updatedBooking.getCustomerName());
        existingBooking.setCustomerEmail(updatedBooking.getCustomerEmail());
        existingBooking.setStartTime(updatedBooking.getStartTime());
        existingBooking.setEndTime(updatedBooking.getEndTime());
        existingBooking.setNotes(updatedBooking.getNotes());
        
        // Update the modification timestamp
        existingBooking.setUpdatedAt(OffsetDateTime.now());

        // Save updated booking to database and return as response DTO
        return bookingMapper.toResponseDto(bookingRepository.save(existingBooking));
    }

    // Cancels a booking by setting status to CANCELLED
    public BookingResponseDto cancelBooking(Long id) {
        // Fetch booking or throw 404 Not Found exception
        BookingEntity existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        // Set booking status to CANCELLED
        existingBooking.setStatus(BookingStatus.CANCELLED);
        
        // Update the modification timestamp
        existingBooking.setUpdatedAt(OffsetDateTime.now());

        // Save updated booking to database and return as response DTO
        return bookingMapper.toResponseDto(bookingRepository.save(existingBooking));
    }

    // Deletes a booking by ID; throws exception if not found
    public void deleteBooking(Long id) {
        // Fetch booking or throw 404 Not Found exception
        BookingEntity booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        
        // Delete booking from database
        bookingRepository.delete(booking);
    }


    // ========== HELPER VALIDATION METHODS ==========
    
    // Validates that times are valid (start not in past, end after start)
    private void validateTime(OffsetDateTime startTime, OffsetDateTime endTime) {
        OffsetDateTime now = OffsetDateTime.now();
        
        // Check if start time is in the past
        if (startTime.isBefore(now)) {
            throw new BadRequestException("Start time cannot be in the past");
        }
        
        // Check if end time is after start time
        if (!endTime.isAfter(startTime)) {
            throw new BadRequestException("End time must be after start time");
        }
    }


    // Validates that referenced staff profile and service exist in database
    private void validateStaffAndService(Long staffProfileId, Long serviceId) {
        // Check if staff profile exists
        staffProfileRepository.findById(staffProfileId)
            .orElseThrow(() -> new ResourceNotFoundException("Staff profile not found"));

        // Check if service exists
        serviceRepository.findById(serviceId)
            .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
    }

    // Checks if staff member has any overlapping bookings in the given time range
    private void validateNoOverlap(Long staffProfileId, OffsetDateTime startTime, OffsetDateTime endTime) {
        // Query for any existing bookings that overlap with requested time range
        if (bookingRepository.existsByStaffProfileIdAndStartTimeLessThanAndEndTimeGreaterThan(
                staffProfileId,
                endTime,
                startTime            
        )) {
            throw new ConflictException("Booking overlaps with existing booking");
        }
    }

    // Checks for overlapping bookings excluding the current booking being updated
    private void validateNoOverlapExcludingCurrent(Long bookingId, Long staffProfileId, OffsetDateTime startTime, OffsetDateTime endTime) {
        // Query for any existing bookings that overlap, but exclude the booking being updated
        if (bookingRepository.existsByStaffProfileIdAndStartTimeLessThanAndEndTimeGreaterThanAndIdNot(
            staffProfileId,
            endTime,
            startTime,
            bookingId
                           
        )) {
            throw new ConflictException("Booking overlaps with existing booking");
        }
    }

    // Validates that booking has either a registered customer or a customer name
    private void validateCustomer(BookingRequestDto bookingRequestDto) {
        boolean hasCustomerUserId = bookingRequestDto.getCustomerUserId() != null;
        boolean hasCustomerName = bookingRequestDto.getCustomerName() != null
                && !bookingRequestDto.getCustomerName().trim().isEmpty();

        // At least one customer identifier must be provided
        if (!hasCustomerUserId && !hasCustomerName) {
            throw new BadRequestException("Booking must have either a registered customer or a customer name");
        }
    }

    // Validates that user references (creator and optional customer) exist in database
    private void validateUserReferences(BookingRequestDto bookingRequestDto) {
        // Check if the user who created the booking exists
        userRepository.findById(bookingRequestDto.getCreatedByUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Created by user not found"));

        // If customer user ID is provided, verify that user exists
        if (bookingRequestDto.getCustomerUserId() != null) {
            userRepository.findById(bookingRequestDto.getCustomerUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer user not found"));
        }
    }
       
}

 
        

       