package com.laundry.walla.controller;

import com.laundry.walla.dto.SubscriberRequest;
import com.laundry.walla.model.Subscriber;
import com.laundry.walla.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/subscribe")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<?> subscribe(@Valid @RequestBody SubscriberRequest request) {
        try {
            Subscriber subscriber = subscriptionService.subscribe(request);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Successfully subscribed!");
            response.put("subscriber", subscriber);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> unsubscribe(@PathVariable String email) {
        boolean unsubscribed = subscriptionService.unsubscribe(email);
        Map<String, Object> response = new HashMap<>();
        if (unsubscribed) {
            response.put("success", true);
            response.put("message", "Successfully unsubscribed");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Email not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/check/{email}")
    public ResponseEntity<?> checkSubscription(@PathVariable String email) {
        boolean subscribed = subscriptionService.isSubscribed(email);
        Map<String, Object> response = new HashMap<>();
        response.put("email", email);
        response.put("subscribed", subscribed);
        return ResponseEntity.ok(response);
    }
}