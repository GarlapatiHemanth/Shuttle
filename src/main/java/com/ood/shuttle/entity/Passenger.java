package com.ood.shuttle.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Passenger {

    @Id
    @GeneratedValue
    private Long id;

    private Long suid;
    private String address;

    private int shuttleId;

    private LocalDateTime dateTime;

    @Transient
    private float distance;





}
