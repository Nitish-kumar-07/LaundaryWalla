package com.laundry.walla.service;

import com.laundry.walla.dto.SubscriberRequest;
import com.laundry.walla.model.Subscriber;

public interface SubscriptionService {
    Subscriber subscribe(SubscriberRequest request);
    boolean unsubscribe(String email);
    boolean isSubscribed(String email);
}