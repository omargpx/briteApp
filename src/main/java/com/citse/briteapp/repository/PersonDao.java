package com.citse.briteapp.repository;

import com.citse.briteapp.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonDao extends JpaRepository<Person,Integer> {
    List<Person> findTop10ByNameContains(String name);
    Person findByCodeContains(String code);

    @Query(value = "select * from tma_person where user_id= :userId ;",nativeQuery = true)
    Person selectPersonByUserId(@Param("userId") Integer userId);
}
