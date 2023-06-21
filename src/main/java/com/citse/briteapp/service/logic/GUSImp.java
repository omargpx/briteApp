package com.citse.briteapp.service.logic;

import com.citse.briteapp.entity.Subscription;
import com.citse.briteapp.entity.User;
import com.citse.briteapp.error.GUSException;
import com.citse.briteapp.model.GUSResponse;
import com.citse.briteapp.model.Servants;
import com.citse.briteapp.repository.SubscriptionDao;
import com.citse.briteapp.repository.UserDao;
import com.citse.briteapp.service.GUSMethods;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Service
public class GUSImp implements GUSMethods {

    //region ATTRIBUTES
    @Autowired
    private SubscriptionDao subscriptionDao;
    @Autowired
    private UserDao userDao;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ012345678987654321abcdefghijklmnñopqrstuvxyz";
    private static final SecureRandom sr = new SecureRandom();
    //endregion

    @Override
    public String genSecureCode(String acronym ) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            int index = sr.nextInt(ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(index));
        }
        return acronym+getCurrentYear()+"-"+builder.toString();
    }

    @Override
    public String genCodeWorkshop() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 18; i++) {
            int index = sr.nextInt(ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(index));
        }
        return builder.toString();
    }

    @Override
    public String getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return Integer.toString(year).substring(2);
    }

    @Override
    public Map<String, String> getSpecificHeaders(HttpServletRequest request) {
        Map<String, String> specificHeaders = new HashMap<>();
        specificHeaders.put("connection", request.getHeader("connection"));
        specificHeaders.put("sec-ch-ua", request.getHeader("sec-ch-ua"));
        specificHeaders.put("sec-ch-ua-platform", request.getHeader("sec-ch-ua-platform"));
        specificHeaders.put("hook_info","/info");
        return specificHeaders;
    }

    @Override
    public Object OAuthAccountLoginCredential(String email) {
        LocalDate today = LocalDate.now();
        User user_credential = userDao.findByEmailContains(email);
        if(user_credential==null || !email.equals(user_credential.getEmail()))
            throw new GUSException(Servants.GUS_SERVICE.name(), null,HttpStatus.NOT_FOUND);
        if(null!=user_credential.getPerson())
            user_credential.setSubscribed(isSubscribed(user_credential.getPerson().getId()));
        user_credential.setLastConnect(today);
        userDao.save(user_credential);
        return user_credential;
    }

    @Override
    public GUSResponse getResponse(HttpServletRequest url, String className, Object data, HttpStatus status) {
        return new GUSResponse(url.getRequestURI(),className,data,status.name());
    }

    private boolean isSubscribed(int personId) {
        Subscription subscription = subscriptionDao.findByPerson(personId);
        return null != subscription;
    }
}
