package com.ood.shuttle.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class travelDetails {

    @Id
    private int id;

    private int shuttleId;

    private int studentId;

    private String destination;

    private Date date;

}
