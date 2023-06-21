package com.citse.briteapp.controller;

import com.citse.briteapp.entity.Person;
import com.citse.briteapp.error.GUSException;
import com.citse.briteapp.model.Servants;
import com.citse.briteapp.service.GUSMethods;
import com.citse.briteapp.service.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService service;
    @Autowired
    private GUSMethods gus;
    private static final String service_name = Servants.PERSON_SERVICE.name();
    @Autowired
    private Environment env;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(name = "token",required = false)String TOKEN,
                                    @RequestParam(name = "id",required = false)Integer id,
                                    @RequestParam(name = "code",required = false)String code,
                                    @RequestParam(name = "name",required = false)String name,
                                    HttpServletRequest request){
        if(!Objects.equals(TOKEN, env.getProperty("config.brite-access.security-token-permission")))
            throw new GUSException(service_name, null, HttpStatus.UNAUTHORIZED);
        if(null!=id)
            return ResponseEntity.ok(gus.getResponse(request,service_name,service.getById(id),HttpStatus.OK));
        if(null!=code)
            return ResponseEntity.ok(gus.getResponse(request,service_name,service.getByCode(code),HttpStatus.OK));
        if(null!=name)
            return ResponseEntity.ok(gus.getResponse(request,service_name,service.findByName(name),HttpStatus.OK));
        return ResponseEntity.ok(gus.getResponse(request,service_name,service.getAll(),HttpStatus.OK));
    }

    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody Person person, HttpServletRequest request,
                                  @RequestParam(name = "email",required = false)String email,
                                  @RequestParam(name = "username",required = false)String username,
                                  @RequestParam(name = "token",required = false)String TOKEN){
        if(!Objects.equals(TOKEN, env.getProperty("config.brite-access.security-token-permission")))
            throw new GUSException(service_name, null, HttpStatus.UNAUTHORIZED);
        return ResponseEntity.ok(gus.getResponse(request,service_name,service.save(person,email,username),HttpStatus.OK));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestParam(name = "id",required = false)Integer id,
                                    @RequestBody Person p, HttpServletRequest request,
                                    @RequestParam(name = "token",required = false)String TOKEN){
        if(!Objects.equals(TOKEN, env.getProperty("config.brite-access.security-token-permission")))
            throw new GUSException(service_name, null, HttpStatus.UNAUTHORIZED);
        Person person = service.getById(id);
        person.setName(p.getName() != null? p.getName() : person.getName());
        person.setIdentification(p.getIdentification() != null? p.getIdentification() : person.getIdentification());
        return ResponseEntity.ok(gus.getResponse(request,service_name,service.save(person),HttpStatus.OK));
    }
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam(name = "id",required = false)Integer id,
                                    @RequestParam(name = "token",required = false)String TOKEN,
                                    HttpServletRequest request){
        if(!Objects.equals(TOKEN, env.getProperty("config.brite-access.security-token-permission")))
            throw new GUSException(service_name, null, HttpStatus.UNAUTHORIZED);
        service.deleteById(id);
        return ResponseEntity.ok(gus.getResponse(request,service_name,"it was removed",HttpStatus.OK));
    }
}
