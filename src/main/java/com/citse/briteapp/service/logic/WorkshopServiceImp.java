package com.citse.briteapp.service.logic;

import com.citse.briteapp.entity.Workshop;
import com.citse.briteapp.error.GUSException;
import com.citse.briteapp.model.Servants;
import com.citse.briteapp.repository.WorkshopDao;
import com.citse.briteapp.service.GUSMethods;
import com.citse.briteapp.service.WorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkshopServiceImp implements WorkshopService {
    @Autowired
    private WorkshopDao repo;
    @Autowired
    private GUSMethods gus;

    @Override
    public List<Workshop> getAll() {
        List<Workshop> workshops = repo.findAll();
        if (!workshops.isEmpty())
            return workshops;
        throw new GUSException(Servants.WORKSHOP_SERVICE.name(), null, HttpStatus.NO_CONTENT);
    }

    @Override
    public Workshop getById(int id) {
        Workshop workshop = repo.findById(id).orElse(null);
        if(null!=workshop)
            return workshop;
        throw new GUSException(Servants.WORKSHOP_SERVICE.name(), null, HttpStatus.NOT_FOUND);
    }

    @Override
    public Workshop save(Workshop workshop) {
        workshop.setCode(gus.genCodeWorkshop());
        return repo.save(workshop);
    }

    @Override
    public void deleteById(int id) {
        repo.deleteById(id);
    }

    @Override
    public List<Workshop> findByPlace(String place) {
        List<Workshop> workshops = repo.findTop10ByPlaceContains(place);
        if (!workshops.isEmpty())
            return workshops;
        throw new GUSException(Servants.WORKSHOP_SERVICE.name(), null, HttpStatus.NOT_FOUND);
    }

    @Override
    public List<Workshop> findByName(String name) {
        List<Workshop> workshops = repo.findTop5ByNameContains(name);
        if (!workshops.isEmpty())
            return workshops;
        throw new GUSException(Servants.WORKSHOP_SERVICE.name(), null, HttpStatus.NOT_FOUND);
    }
}
