package com.ciccone.backend.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ciccone.backend.dto.BookingMapper;
import com.ciccone.backend.dto.BookingRequestDto;
import com.ciccone.backend.dto.BookingResponseDto;
import com.ciccone.backend.entity.BookingEntity;
import com.ciccone.backend.entity.BookingStatus;
import com.ciccone.backend.repository.BookingRepository;


@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public BookingService(BookingRepository bookingRepository, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
    }

    public BookingResponseDto createBooking(BookingRequestDto bookingRequestDto) {
        BookingEntity bookingEntity = bookingMapper.toEntity(bookingRequestDto);
        OffsetDateTime now = OffsetDateTime.now();
        bookingEntity.setCreatedAt(now);
        bookingEntity.setUpdatedAt(now);
        bookingEntity.setStatus(BookingStatus.REQUESTED);
        return bookingMapper.toResponseDto(bookingRepository.save(bookingEntity));
    }
    
    public List<BookingResponseDto> getAllBookings() {
        return bookingRepository.findAll().stream()
            .map(bookingMapper::toResponseDto)
            .toList();
    }

    public BookingResponseDto getBookingById(Long id) {
        return bookingMapper.toResponseDto(bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found")));
    }

    public BookingResponseDto updateBooking(Long id, BookingRequestDto updatedBooking) {

        BookingEntity bookingEntity = bookingMapper.toEntity(updatedBooking);

        BookingEntity existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        existingBooking.setServiceId(bookingEntity.getServiceId());
        existingBooking.setStaffProfileId(bookingEntity.getStaffProfileId());
        existingBooking.setCustomerName(bookingEntity.getCustomerName());
        existingBooking.setCustomerEmail(bookingEntity.getCustomerEmail());
        existingBooking.setStartTime(bookingEntity.getStartTime());
        existingBooking.setEndTime(bookingEntity.getEndTime());
        existingBooking.setNotes(bookingEntity.getNotes());
        existingBooking.setUpdatedAt(OffsetDateTime.now());

        return bookingMapper.toResponseDto(bookingRepository.save(existingBooking));
    }

    public void deleteBooking(Long id) {
        BookingEntity booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        bookingRepository.delete(booking);
    }

}
