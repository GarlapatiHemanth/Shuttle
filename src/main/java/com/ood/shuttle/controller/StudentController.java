package com.ood.shuttle.controller;


import com.ood.shuttle.model.StudentModel;
import com.ood.shuttle.service.StudentServices;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ip")
public class StudentController {

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    StudentServices studentServices;

    @GetMapping("/requestPickup")
    public ResponseEntity<Object> findStudent(@RequestParam(name = "suid") Long id) {

        log.info("start of findStudent()");

        return studentServices.findStudent(id);
    }


    @PostMapping("/addStudent")
    public ResponseEntity<Object> addStudent(@RequestBody @Valid List<StudentModel> students) {

        log.info("start of addStudent()");

        return studentServices.addStudent(students);
    }
}
