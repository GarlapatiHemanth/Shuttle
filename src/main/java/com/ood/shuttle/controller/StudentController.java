package com.ood.shuttle.controller;


import com.ood.shuttle.entity.Student;
import com.ood.shuttle.service.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(name = "/id")
public class StudentController {

    @Autowired
    StudentServices studentServices;

    @GetMapping("/student")
    public ResponseEntity<Object> findStudent(@RequestParam(name = "suid") Long id) {


        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/addStudent")
    public ResponseEntity<Object> addStudent(@RequestBody Student student) {


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
