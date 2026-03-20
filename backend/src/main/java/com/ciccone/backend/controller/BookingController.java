package com.ciccone.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ciccone.backend.dto.BookingRequestDto;
import com.ciccone.backend.dto.BookingResponseDto;
import com.ciccone.backend.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }   


    @PostMapping
    public BookingResponseDto createBooking(@RequestBody @Valid BookingRequestDto bookingRequestDto) {
        return bookingService.createBooking(bookingRequestDto);
    }

    @GetMapping
    public List<BookingResponseDto> getAllBookings() {
        return bookingService.getAllBookings();
    }       

    @GetMapping("/{id}")
    public BookingResponseDto getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @PutMapping("/{id}")
    public BookingResponseDto updateBooking(@PathVariable Long id, @RequestBody @Valid BookingRequestDto updatedBooking) {
        return bookingService.updateBooking(id, updatedBooking);
    }   

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }
    
}
