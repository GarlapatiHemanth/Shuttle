package com.ood.shuttle.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.aspectj.lang.annotation.Aspect;

@Entity
@Getter
@Setter
@Table(name = "STUDENT")
public class Student {

    @Id
    private long suid;
    private String name="hemanth";
    private String netId;
    private String address;


//    public static void main(String args[]){
//        String s=new String("hi");
//        String s2=new String("hi");
//        System.out.println(s.hashCode()==s2.hashCode());
//        Student s1=new Student();
//        String x=s1.getName();
//        System.out.println(x);
//        x="bye";
//        System.out.println(x);
//        System.out.println(s1.getName());
//    }


}
