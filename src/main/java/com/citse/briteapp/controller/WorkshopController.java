package com.citse.briteapp.controller;

import com.citse.briteapp.entity.Subscription;
import com.citse.briteapp.entity.Workshop;
import com.citse.briteapp.error.GUSException;
import com.citse.briteapp.model.Servants;
import com.citse.briteapp.service.GUSMethods;
import com.citse.briteapp.service.SubscriptionService;
import com.citse.briteapp.service.WorkshopService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/workshop")
public class WorkshopController {
    @Autowired
    private WorkshopService service;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private GUSMethods gus;
    private static final String service_name = Servants.WORKSHOP_SERVICE.name();
    @Autowired
    private Environment env;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(name = "id",required = false)Integer id,
                                    @RequestParam(name = "name",required = false)String name,
                                    @RequestParam(name = "place",required = false)String place,
                                    HttpServletRequest request){
        if(null!=id)
            return ResponseEntity.ok(gus.getResponse(request,service_name,service.getById(id), HttpStatus.OK));
        if(null!=name)
            return ResponseEntity.ok(gus.getResponse(request,service_name,service.findByName(name), HttpStatus.OK));
        if(null!=place)
            return ResponseEntity.ok(gus.getResponse(request,service_name,service.findByPlace(place), HttpStatus.OK));
        return ResponseEntity.ok(gus.getResponse(request,service_name,service.getAll(), HttpStatus.OK));
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestParam(name = "token",required = false)String TOKEN,
                                  @RequestBody Workshop workshop, HttpServletRequest request){
        if(!Objects.equals(TOKEN, env.getProperty("config.brite-access.security-token-permission")))
            throw new GUSException(service_name, null, HttpStatus.UNAUTHORIZED);
        return ResponseEntity.ok(gus.getResponse(request,service_name,service.save(workshop),HttpStatus.OK));
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestParam(name = "token",required = false)String TOKEN,
                                       @RequestBody Subscription subscription,HttpServletRequest request){
        if(!Objects.equals(TOKEN, env.getProperty("config.brite-access.security-token-permission")))
            throw new GUSException(service_name, null, HttpStatus.UNAUTHORIZED);
        return ResponseEntity.ok(gus.getResponse(request,service_name,subscriptionService.save(subscription),HttpStatus.OK));
    }
}
