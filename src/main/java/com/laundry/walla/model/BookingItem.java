package com.laundry.walla.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "booking_items")
@Data
@NoArgsConstructor
public class BookingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(name = "service_name", nullable = false)
    private String serviceName;

    @Column(name = "service_price", precision = 10, scale = 2)
    private BigDecimal servicePrice;
}