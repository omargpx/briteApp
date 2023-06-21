package com.citse.briteapp.service;

import com.citse.briteapp.entity.Person;
import com.citse.briteapp.entity.Subscription;

import java.util.List;

public interface SubscriptionService {
    List<Subscription> getAll();
    Subscription getById(int id);
    Subscription save(Subscription subscription);
    void delete(int id);

    // filters and more
    Subscription findByCode(String code);
}
