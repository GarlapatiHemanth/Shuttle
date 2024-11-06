package com.ood.shuttle.service;

import com.ood.shuttle.BO.DropOffInterface;
import com.ood.shuttle.BO.ShuttleObserver;
import com.ood.shuttle.BO.impl.ShuttleInterfaceImpl;
import com.ood.shuttle.entity.Passenger;
import com.ood.shuttle.entity.ShuttleLocation;
import com.ood.shuttle.model.ShuttleModel;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShuttleServices {

    private static final Logger log = LoggerFactory.getLogger(ShuttleServices.class);
    @Autowired
    ShuttleInterfaceImpl shuttleInterfaceImpl;

    @Autowired
    DropOffInterface dropOffHandler;

    @Qualifier("dropOffHandler")
    @Autowired
    ShuttleObserver observer;


    @Qualifier("locationSaver")
    @Autowired
    ShuttleObserver locationObserver;

    @Value("${collegePlace.Latitude}")
    private double collegePlaceLatitude;

    @Value("${collegePlace.Longitude}")
    private double collegePlaceLongitude;

    @PostConstruct
    public void initializeShuttle() {
        if (ShuttleModel.One.getObservers().isEmpty()){
            ShuttleModel.One.addObserver(observer);
            ShuttleModel.One.addObserver(locationObserver);
        }

    }


    public ResponseEntity<Object> addPassengerToShuttle(Long suid,String address){

        try{
            if (suid == null || !String.valueOf(suid).matches("^[1-9]\\d{8}$")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            return shuttleInterfaceImpl.checkAndAddPassenger(suid,address);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateShuttleLocation( double longitude,double latitude){
        try{

            //assuming 0 for college place or initial bus stop and removing all passengers
            if(longitude == collegePlaceLongitude && latitude == collegePlaceLatitude &&
                    ShuttleModel.One.getLongitude()!=collegePlaceLongitude && ShuttleModel.One.getLatitude()!=collegePlaceLatitude){
                dropOffHandler.handleDropOff(ShuttleModel.One.getPassengers());
            }

            ShuttleModel.One.setLocation(longitude,latitude);
        }catch (Exception e){
            System.out.println(e);
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
            log.error(String.valueOf(e));
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
