package com.laundry.walla.service;

import com.laundry.walla.dto.SubscriberRequest;
import com.laundry.walla.model.Subscriber;
import com.laundry.walla.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriberRepository subscriberRepository;

    @Override
    public Subscriber subscribe(SubscriberRequest request) {
        if (subscriberRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already subscribed");
        }

        Subscriber subscriber = new Subscriber();
        subscriber.setFullName(request.getFullName());
        subscriber.setEmail(request.getEmail());

        return subscriberRepository.save(subscriber);
    }

    @Override
    public boolean unsubscribe(String email) {
        return subscriberRepository.findByEmail(email)
                .map(subscriber -> {
                    subscriberRepository.delete(subscriber);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean isSubscribed(String email) {
        return subscriberRepository.existsByEmail(email);
    }
}