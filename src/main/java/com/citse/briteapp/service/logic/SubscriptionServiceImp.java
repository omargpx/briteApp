package com.citse.briteapp.service.logic;

import com.citse.briteapp.entity.Person;
import com.citse.briteapp.entity.Subscription;
import com.citse.briteapp.error.GUSException;
import com.citse.briteapp.model.Servants;
import com.citse.briteapp.repository.PersonDao;
import com.citse.briteapp.repository.SubscriptionDao;
import com.citse.briteapp.service.GUSMethods;
import com.citse.briteapp.service.PersonService;
import com.citse.briteapp.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class SubscriptionServiceImp implements SubscriptionService {

    @Autowired
    private SubscriptionDao repo;
    @Autowired
    private PersonService personService;
    @Autowired
    private GUSMethods gus;

    @Override
    public List<Subscription> getAll() {
        List<Subscription> subscriptions = repo.findAll();
        if(!subscriptions.isEmpty())
            return subscriptions;
        throw new GUSException(Servants.SUBSCRIPTION_SERVICE.name(), null, HttpStatus.NO_CONTENT);
    }

    @Override
    public Subscription getById(int id) {
        Subscription subscription = repo.findById(id).orElse(null);
        if (null!=subscription)
            return subscription;
        throw new GUSException(Servants.SUBSCRIPTION_SERVICE.name(), null, HttpStatus.NOT_FOUND);
    }

    @Override
    public Subscription save(Subscription subscription) {
        Person person = personService.getById(subscription.getPerson().getId());
        if (person.getCupLimit()<=0)
            throw new GUSException(Servants.SUBSCRIPTION_SERVICE.name(),"LIMITE ALCANZADO",HttpStatus.PARTIAL_CONTENT);
        LocalDate today = LocalDate.now();
        subscription.setDate(today);
        Random random = new Random();
        subscription.setCode(String.valueOf(random.nextInt(999999)));
        return repo.save(subscription);
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
    }

    @Override
    public Subscription findByCode(String code) {
        Subscription subscription = repo.findByCodeContains(code);
        if (null!=subscription)
            return subscription;
        throw new GUSException(Servants.SUBSCRIPTION_SERVICE.name(), null, HttpStatus.NOT_FOUND);
    }

}
