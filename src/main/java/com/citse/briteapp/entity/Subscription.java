package com.citse.briteapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "tmv_subscription")
public class Subscription implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subscription")
    private int id;
    @Column(name = "fe_subscription")
    private LocalDate date;
    @Column(name = "co_ticket")
    private String code;

    @JsonIgnoreProperties({"user","subscriptions"})
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @JsonIgnoreProperties({"event","subscriptions"})
    @ManyToOne
    @JoinColumn(name = "workshop_id")
    private Workshop workshop;
}
