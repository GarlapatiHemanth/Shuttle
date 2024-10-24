package com.ood.shuttle.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StudentModel {

    private long suid;
        private String name;
        private String netId;
        private String address;


}
