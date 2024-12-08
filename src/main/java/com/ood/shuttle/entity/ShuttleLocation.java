package com.ood.shuttle.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ShuttleLocation {


    @Id
    private long id;

    private double latitude;

    private double longitude;

    private LocalDateTime dateTime;
}
