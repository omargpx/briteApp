package com.citse.briteapp.service;

import com.citse.briteapp.model.GUSResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.util.Map;

public interface GUSMethods {
    String genSecureCode(String acronym);
    String genCodeWorkshop();
    String getCurrentYear();
    Map<String, String> getSpecificHeaders(HttpServletRequest request);
    Object OAuthAccountLoginCredential(String email);
    GUSResponse getResponse(HttpServletRequest url, String className, Object data, HttpStatus status);
}
