package com.citse.briteapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "tma_workshop")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Workshop implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_event")
    private int id;
    @Column(name = "no_event")
    private String name;
    @Column(name = "no_place")
    private String place;
    @Column(name = "no_exhibitor")
    private String speaker;
    @Column(name = "co_workshop")
    private String code;
    @Column(name = "nu_limit")
    private int limit;
    @Column(name = "ti_event")
    private int type; // event type
    @Column(name = "fe_date")
    private LocalDateTime dateTime;

    @JsonIgnoreProperties({"subscriptions"})
    @ManyToOne
    @JoinColumn(name = "event_id",referencedColumnName = "id_event")
    private Workshop event;

    @JsonIgnoreProperties({"person","workshop"})
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "workshop")
    private List<Subscription> subscriptions;

}

