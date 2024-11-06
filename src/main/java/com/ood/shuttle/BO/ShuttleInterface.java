package com.ood.shuttle.BO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//template design pattern
public interface ShuttleInterface {

    default ResponseEntity<Object> checkAndAddPassenger(Long suid,String address){
        try{
            boolean result=checkPassenger(suid);
            if(result){
                addPassenger(suid,address);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Student Not Found", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    boolean checkPassenger(Long suid);
    void addPassenger(Long suid, String address);
}
