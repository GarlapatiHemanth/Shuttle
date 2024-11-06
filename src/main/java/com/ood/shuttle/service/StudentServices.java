package com.ood.shuttle.service;

import com.ood.shuttle.controller.StudentController;
import com.ood.shuttle.entity.Student;
import com.ood.shuttle.model.StudentModel;
import com.ood.shuttle.model.StudentStatus;
import com.ood.shuttle.repo.StudentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentServices {

    private static final Logger log = LoggerFactory.getLogger(StudentServices.class);

    @Autowired
    StudentRepo studentRepo;

    public ResponseEntity<Object> findStudent(Long id) {

        try{
            if (id == null || !String.valueOf(id).matches("^[1-9]\\d{8}$")) {
                return new ResponseEntity<>("Student Not Found",HttpStatus.BAD_REQUEST);
            }
            return studentRepo.findById(id).isPresent()? ResponseEntity.ok("ETA for Shuttle is 5 min") :
                    new ResponseEntity<>("SUID not found",HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<>("Service Unavailable", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> addStudent(List<StudentModel> studentModelList) {
        try{
            if(!studentModelList.isEmpty()){
                List<Student> studentList=new ArrayList<>();
                for (StudentModel studentModel : studentModelList) {
                    studentList.add(studentModelAdapter(studentModel,StudentStatus.Idle));
                }

                studentRepo.saveAll(studentList);
                return new ResponseEntity<>("Success", HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<>("Data is Required",HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private Student studentModelAdapter(StudentModel studentModel, StudentStatus studentStatus){
        Student student = new Student();
        student.setSuid(Long.parseLong(studentModel.getSuid()));
        student.setName(studentModel.getName());
        student.setAddress(studentModel.getAddress());
        student.setNetId(studentModel.getNetId());
        student.setStatus(studentStatus.name());
        return student;
    }
}
