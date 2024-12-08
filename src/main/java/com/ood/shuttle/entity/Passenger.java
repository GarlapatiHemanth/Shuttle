package com.ood.shuttle.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Passenger {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "suid", referencedColumnName = "suid")
    private Student student;

    private String address;

    private LocalDateTime dateTime;

    @Transient
    private float distance;

    //used by contains in dropOffHandler

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passenger passenger)) return false;
        return Objects.equals(this.student.getSuid(), passenger.student.getSuid()) && Objects.equals(address, passenger.address) && Objects.equals(dateTime, passenger.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student.getSuid(), address, dateTime);
    }
}
