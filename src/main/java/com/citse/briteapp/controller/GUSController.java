package com.citse.briteapp.controller;

import com.citse.briteapp.error.GUSException;
import com.citse.briteapp.model.InfoResponse;
import com.citse.briteapp.model.Servants;
import com.citse.briteapp.service.GUSMethods;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController
@RequestMapping
public class GUSController {

    @Autowired
    private GUSMethods gus;
    @Autowired
    private Environment env;
    private final ResourceLoader resourceLoader;

    public GUSController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    @RequestMapping
    public ResponseEntity<?> handleWelcome(HttpServletRequest request){
        String url = request.getRequestURL().toString();
        InfoResponse response = new InfoResponse(url,"Welcome to BriteApp",gus.getSpecificHeaders(request));
        return new ResponseEntity<>(response, HttpStatus.PARTIAL_CONTENT);
    }
    @RequestMapping(value = "/info",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> handleInfo(HttpServletRequest request) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:info.json");
        String jsonContent = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        return new ResponseEntity<>(jsonContent, HttpStatus.PARTIAL_CONTENT);
    }
    @RequestMapping(value = "/avatar",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> handleAvatars(HttpServletRequest request) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = resourceLoader.getResource("classpath:avatars.json");
        Object jsonObject = objectMapper.readValue(resource.getInputStream(), Object.class);
        return ResponseEntity.ok(gus.getResponse(request,Servants.GUS_SERVICE.name(),jsonObject,HttpStatus.PARTIAL_CONTENT));
    }
    @RequestMapping(value = "/auth",method = RequestMethod.POST)
    public ResponseEntity<?> authAccountLogin(HttpServletRequest request,
                                              @RequestParam(name = "token")String TOKEN,
                                              @RequestParam(name = "email")String email){
        if(!Objects.equals(TOKEN, env.getProperty("config.brite-access.security-token-permission")))
            throw new GUSException(Servants.GUS_SERVICE.name(), null, HttpStatus.UNAUTHORIZED);
        return ResponseEntity.ok(gus.getResponse(request,Servants.GUS_SERVICE.name(), gus.OAuthAccountLoginCredential(email),HttpStatus.OK));
    }
    @RequestMapping("/**")
    public ResponseEntity<?> handleInvalidRequest(HttpServletRequest request) {
        InfoResponse response = new InfoResponse(request.getRequestURL().toString(),"Non-existent URL in brite_server. check the path entered",null);
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

}