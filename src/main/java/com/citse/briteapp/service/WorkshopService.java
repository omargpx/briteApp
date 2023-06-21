package com.citse.briteapp.service;

import com.citse.briteapp.entity.Workshop;

import java.util.List;

public interface WorkshopService {
    List<Workshop> getAll();
    Workshop getById(int id);
    Workshop save(Workshop workshop);
    void deleteById(int id);

    // filter
    List<Workshop> findByPlace(String place);
    List<Workshop> findByName(String name);
}
