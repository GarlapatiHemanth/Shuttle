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
public class IdleState implements ShuttleState {
    private static final Logger log = LoggerFactory.getLogger(IdleState.class);

    @Setter
    ShuttleService shuttleService;

    DropOffInterface dropOffHandler;

    @Autowired
    public IdleState(DropOffInterface dropOffHandler) {
        this.dropOffHandler=dropOffHandler;
    }

    @Override
    public ResponseEntity<Object> addPassengerToShuttle(Long suid, String address) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateShuttleLocation(double longitude, double latitude) {

        try{
            dropOffHandler.handleDropOff(shuttleService.getPassengers());
            shuttleService.setLocation(longitude,latitude);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Object> fetchNextPassengerToDropOffShuttle() {
        return new ResponseEntity<>("No Passengers Available", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> dropPassengers(List<Passenger> passengerList) {
        return new ResponseEntity<>("No Passengers Available", HttpStatus.OK);
    }

}
