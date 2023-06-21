package com.citse.briteapp.repository;

import com.citse.briteapp.entity.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkshopDao extends JpaRepository<Workshop,Integer> {
    List<Workshop> findTop10ByPlaceContains(String place);
    List<Workshop> findTop5ByNameContains(String name);
}
