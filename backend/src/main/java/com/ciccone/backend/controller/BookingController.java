package com.ciccone.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.ciccone.backend.dto.BookingRequestDto;
import com.ciccone.backend.dto.BookingResponseDto;
import com.ciccone.backend.service.BookingService;

import jakarta.validation.Valid;

// REST controller that handles all HTTP requests related to booking operations
@RestController
// Maps all booking-related endpoints to the /api/bookings base path
@RequestMapping("/api/bookings")
// Allows requests from the frontend running on localhost:5173 to access these endpoints
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    // Dependency injection: BookingService is injected through constructor
    private final BookingService bookingService;

    // Constructor that accepts BookingService dependency
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // POST endpoint to create a new booking; returns 201 Created status
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponseDto createBooking(@RequestBody @Valid BookingRequestDto bookingRequestDto) {
        // Delegates to service layer to process booking creation with validated input
        return bookingService.createBooking(bookingRequestDto);
    }

    // GET endpoint to retrieve all bookings from the database
    @GetMapping
    public List<BookingResponseDto> getAllBookings() {
        // Returns a list of all bookings in the system
        return bookingService.getAllBookings();
    }

    // GET endpoint to retrieve a specific booking by its ID
    @GetMapping("/{id}")
    public BookingResponseDto getBookingById(@PathVariable Long id) {
        // Retrieves a single booking by ID from the service layer
        return bookingService.getBookingById(id);
    }

    // PUT endpoint to fully update an existing booking's information
    @PutMapping("/{id}")
    public BookingResponseDto updateBooking(@PathVariable Long id, @RequestBody @Valid BookingRequestDto updatedBooking) {
        // Updates booking data with validated input and returns updated booking
        return bookingService.updateBooking(id, updatedBooking);
    }

    // PATCH endpoint to cancel a specific booking
    @PatchMapping("/{id}/cancel")
    public BookingResponseDto cancelBooking(@PathVariable Long id) {
        // Cancels a booking by ID; returns the updated booking with cancelled status
        return bookingService.cancelBooking(id);
    }

    // DELETE endpoint to remove a booking from the database; returns 204 No Content
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooking(@PathVariable Long id) {
        // Deletes a booking by ID; returns no content on success
        bookingService.deleteBooking(id);
    }
}