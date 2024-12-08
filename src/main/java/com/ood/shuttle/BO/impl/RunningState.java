package com.ood.shuttle.BO.impl;

import com.ood.shuttle.BO.DropOffInterface;
import com.ood.shuttle.BO.ShuttleState;
import com.ood.shuttle.entity.Passenger;
import com.ood.shuttle.service.ShuttleService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RunningState implements ShuttleState {

    private static final Logger log = LoggerFactory.getLogger(RunningState.class);

    @Setter
    ShuttleService shuttleService;


    DropOffInterface dropOffHandler;

    @Autowired
    public RunningState(DropOffInterface dropOffHandler) {

        this.dropOffHandler = dropOffHandler;
    }


    public ResponseEntity<Object> addPassengerToShuttle(Long suid, String address){

        try{
            if (suid == null || !String.valueOf(suid).matches("^[1-9]\\d{8}$")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            return shuttleService.checkAndAddPassenger(suid,address);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateShuttleLocation( double longitude,double latitude){
        try{

            shuttleService.setLocation(longitude,latitude);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> fetchNextPassengerToDropOffShuttle(){

        List<Passenger> passenger= dropOffHandler.getNextPassenger();
        if(passenger!=null && !passenger.isEmpty()){
            return new ResponseEntity<>(passenger,HttpStatus.OK);
        }
        return new ResponseEntity<>("No Passenger Available",HttpStatus.OK);
    }

    public ResponseEntity<Object> dropPassengers(List<Passenger> passengerList){
        try {
            dropOffHandler.handleDropOff(passengerList);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
