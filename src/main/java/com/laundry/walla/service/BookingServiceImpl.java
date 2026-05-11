package com.laundry.walla.service;

import com.laundry.walla.dto.BookingRequest;
import com.laundry.walla.dto.BookingResponse;
import com.laundry.walla.model.Booking;
import com.laundry.walla.model.BookingItem;
import com.laundry.walla.repository.BookingRepository;
import com.laundry.walla.repository.BookingItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingItemRepository bookingItemRepository;

    @Override
    public BookingResponse createBooking(BookingRequest request) {
        // Create new booking
        Booking booking = new Booking();
        booking.setFullName(request.getFullName());
        booking.setEmail(request.getEmail());
        booking.setPhone(request.getPhone());
        booking.setTotalAmount(request.getTotalAmount());
        booking.setStatus("Pending");

        // Save booking
        Booking savedBooking = bookingRepository.save(booking);

        // Save cart items
        if (request.getCartItems() != null) {
            for (BookingRequest.CartItem item : request.getCartItems()) {
                BookingItem bookingItem = new BookingItem();
                bookingItem.setBooking(savedBooking);
                bookingItem.setServiceName(item.getService());

                // Parse price (remove ₹ symbol if present)
                String priceStr = item.getPrice().replace("₹", "");
                bookingItem.setServicePrice(new BigDecimal(priceStr));

                bookingItemRepository.save(bookingItem);
            }
        }

        // Return response
        return BookingResponse.builder()
                .bookingId(savedBooking.getBookingId())
                .fullName(savedBooking.getFullName())
                .email(savedBooking.getEmail())
                .phone(savedBooking.getPhone())
                .totalAmount(savedBooking.getTotalAmount())
                .bookingDate(savedBooking.getBookingDate())
                .status(savedBooking.getStatus())
                .message("Booking confirmed successfully!")
                .build();
    }

    @Override
    public BookingResponse getBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        return BookingResponse.builder()
                .bookingId(booking.getBookingId())
                .fullName(booking.getFullName())
                .email(booking.getEmail())
                .phone(booking.getPhone())
                .totalAmount(booking.getTotalAmount())
                .bookingDate(booking.getBookingDate())
                .status(booking.getStatus())
                .build();
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAllByOrderByBookingDateDesc()
                .stream()
                .map(booking -> BookingResponse.builder()
                        .bookingId(booking.getBookingId())
                        .fullName(booking.getFullName())
                        .email(booking.getEmail())
                        .phone(booking.getPhone())
                        .totalAmount(booking.getTotalAmount())
                        .bookingDate(booking.getBookingDate())
                        .status(booking.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getBookingsByEmail(String email) {
        return bookingRepository.findByEmail(email)
                .stream()
                .map(booking -> BookingResponse.builder()
                        .bookingId(booking.getBookingId())
                        .fullName(booking.getFullName())
                        .email(booking.getEmail())
                        .phone(booking.getPhone())
                        .totalAmount(booking.getTotalAmount())
                        .bookingDate(booking.getBookingDate())
                        .status(booking.getStatus())
                        .build())
                .collect(Collectors.toList());
    }
}