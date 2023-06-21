package com.citse.briteapp.repository;

import com.citse.briteapp.entity.Person;
import com.citse.briteapp.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionDao extends JpaRepository<Subscription,Integer> {
    @Query(value = "select * from tmv_subscription where person_id= :personId ;",nativeQuery = true)
    Subscription findByPerson(@Param("personId") Integer personId);

    Subscription findByCodeContains(String code);
}
