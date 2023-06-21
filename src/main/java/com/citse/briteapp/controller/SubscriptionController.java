package com.citse.briteapp.controller;

import com.citse.briteapp.entity.Subscription;
import com.citse.briteapp.error.GUSException;
import com.citse.briteapp.model.Servants;
import com.citse.briteapp.service.GUSMethods;
import com.citse.briteapp.service.SubscriptionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService service;
    @Autowired
    private GUSMethods gus;
    private static final String service_name = Servants.SUBSCRIPTION_SERVICE.name();
    @Autowired
    private Environment env;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(name = "token",required = false)String TOKEN,
                                    @RequestParam(name = "code",required = false)String code,
                                    HttpServletRequest request){
        if(!Objects.equals(TOKEN, env.getProperty("config.brite-access.security-token-permission")))
            throw new GUSException(service_name, null, HttpStatus.UNAUTHORIZED);
        if (null!=code)
            return ResponseEntity.ok(gus.getResponse(request,service_name,service.findByCode(code),HttpStatus.OK));
        return ResponseEntity.ok(gus.getResponse(request,service_name,service.getAll(),HttpStatus.OK));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam(name = "token",required = false)String TOKEN,
                                    @RequestParam(name = "code",required = false)String code){
        if(!Objects.equals(TOKEN, env.getProperty("config.brite-access.security-token-permission")))
            throw new GUSException(service_name, null, HttpStatus.UNAUTHORIZED);
        Subscription subscription = service.findByCode(code);
        service.delete(subscription.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
