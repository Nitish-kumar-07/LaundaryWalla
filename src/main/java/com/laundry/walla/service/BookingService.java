package com.laundry.walla.service;

import com.laundry.walla.dto.BookingRequest;
import com.laundry.walla.dto.BookingResponse;
import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);
    BookingResponse getBooking(Long id);
    List<BookingResponse> getAllBookings();
    List<BookingResponse> getBookingsByEmail(String email);
}