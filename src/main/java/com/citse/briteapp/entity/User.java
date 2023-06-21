package com.citse.briteapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "TSG_USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int id;
    @Column(name = "di_email")
    private String email;
    @Column(name = "username")
    private String username;
    @Column(name = "fe_init")
    private LocalDate init;
    @Column(name = "fe_last_connect")
    private LocalDate lastConnect;

    @JsonIgnoreProperties({"user","subscriptions"})
    @OneToOne(mappedBy = "user", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Person person;

    @Transient
    private boolean isSubscribed;

    public User() {
    }

    @Builder
    public User(String email,String username, LocalDate init) {
        this.email = email;
        this.username = username;
        this.init = init;
    }
}
