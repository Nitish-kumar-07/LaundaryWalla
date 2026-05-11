package com.laundry.walla.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
public class BookingRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
    private String phone;

    private BigDecimal totalAmount;

    @NotNull(message = "Cart items are required")
    private List<CartItem> cartItems;

    @Data
    public static class CartItem {
        private String service;
        private String price;
    }
}