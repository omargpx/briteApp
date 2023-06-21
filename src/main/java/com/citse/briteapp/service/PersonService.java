package com.citse.briteapp.service;

import com.citse.briteapp.entity.Person;

import java.util.List;

public interface PersonService {
    List<Person> getAll();
    Person getById(int id);
    Person save(Person person);
    Person save(Person person,String email,String username);
    void deleteById(int id);

    // filters
    List<Person> findByName(String name);
    Person getByCode(String code);
}
