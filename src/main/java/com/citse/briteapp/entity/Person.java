package com.citse.briteapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "TMA_PERSON")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_person")
    private int id;
    @Column(name = "no_person")
    private String name;
    @Column(name = "co_person")
    private String code;
    @Column(name = "nu_identification")
    private String identification;
    @Column(name = "limit_subscriptions")
    private int cupLimit;
    @Column(name = "di_avatar")
    private String avatar;


    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnoreProperties({"person"})
    @OneToMany(mappedBy = "person")
    private List<Subscription> subscriptions;
}
