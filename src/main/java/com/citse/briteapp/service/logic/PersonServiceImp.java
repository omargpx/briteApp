package com.citse.briteapp.service.logic;

import com.citse.briteapp.entity.Person;
import com.citse.briteapp.entity.User;
import com.citse.briteapp.error.GUSException;
import com.citse.briteapp.repository.PersonDao;
import com.citse.briteapp.model.Servants;
import com.citse.briteapp.repository.UserDao;
import com.citse.briteapp.service.GUSMethods;
import com.citse.briteapp.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PersonServiceImp implements PersonService {

    //region ATTRIBUTES
    @Autowired
    private PersonDao repo;
    @Autowired
    private UserDao userDao;
    @Autowired
    private GUSMethods gus;
    private static final String default_avatar = "i.ibb.co/94KCvP5/avatar1.png";
    //endregion

    @Override
    public List<Person> getAll() {
        List<Person> people = repo.findAll();
        if (!people.isEmpty())
            return people;
        throw new GUSException(Servants.PERSON_SERVICE.name(), null, HttpStatus.NO_CONTENT);
    }

    @Override
    public Person getById(int id) {
        Person person = repo.findById(id).orElse(null);
        if(null!=person)
            return person;
        throw new GUSException(Servants.PERSON_SERVICE.name(), null, HttpStatus.NOT_FOUND);
    }

    @Override
    public Person save(Person person) {
        if(null==person.getAvatar())
            person.setAvatar(default_avatar);
        return repo.save(person);
    }

    @Override
    public Person save(Person person,String email,String username) {
        LocalDate today = LocalDate.now();
        person.setCode(gus.genSecureCode("BT"));
        person.setCupLimit(3);// limit of attempts
        if(null==person.getAvatar())
            person.setAvatar(default_avatar);
        User user = userDao.save(User.builder().email(email).username(username).init(today).build());
        person.setUser(user);
        return repo.save(person);
    }

    @Override
    public void deleteById(int id) {
        repo.deleteById(id);
    }

    @Override
    public List<Person> findByName(String name) {
        List<Person> people = repo.findTop10ByNameContains(name);
        if (!people.isEmpty())
            return people;
        throw new GUSException(Servants.PERSON_SERVICE.name(), null, HttpStatus.NOT_FOUND);
    }

    @Override
    public Person getByCode(String code) {
        Person person = repo.findByCodeContains(code);
        if (null!=person)
            return person;
        throw new GUSException(Servants.PERSON_SERVICE.name(), null, HttpStatus.NOT_FOUND);
    }
}
