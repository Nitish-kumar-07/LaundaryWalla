package com.laundry.walla.repository;

import com.laundry.walla.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByOrderByBookingDateDesc();
    List<Booking> findByEmail(String email);
    List<Booking> findByPhone(String phone);
}