package com.laundry.walla.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class BookingResponse {
    private Long bookingId;
    private String fullName;
    private String email;
    private String phone;
    private BigDecimal totalAmount;
    private LocalDateTime bookingDate;
    private String status;
    private String message;
}