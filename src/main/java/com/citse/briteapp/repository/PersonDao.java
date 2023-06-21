package com.citse.briteapp.repository;

import com.citse.briteapp.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonDao extends JpaRepository<Person,Integer> {
    List<Person> findTop10ByNameContains(String name);
    Person findByCodeContains(String code);
}
