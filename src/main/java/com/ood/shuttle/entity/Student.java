package com.ood.shuttle.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "STUDENT")
public class Student {

    @Id
    private long suid;
    private String name;
    private String netId;
    private String address;
    private String status;


}
