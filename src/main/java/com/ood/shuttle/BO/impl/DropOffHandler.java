package com.ood.shuttle.BO.impl;

import com.ood.shuttle.BO.DropOffInterface;
import com.ood.shuttle.BO.ShuttleObserver;
import com.ood.shuttle.entity.Passenger;
import com.ood.shuttle.model.ShuttleModel;
import com.ood.shuttle.repo.PassengersRepo;
import com.ood.shuttle.repo.ShuttleLocationRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class DropOffHandler implements ShuttleObserver, DropOffInterface {

    private static final Logger log = LoggerFactory.getLogger(DropOffHandler.class);


    private ShuttleModel shuttleModel;

    @Autowired
    ShuttleLocationRepo shuttleLocationRepo;

    @Autowired
    PassengersRepo passengersRepo;

    public DropOffHandler() {
        this.shuttleModel=ShuttleModel.One;
    }


    @Override
    public void updateShuttle(ShuttleModel shuttle) {

        this.shuttleModel = shuttle;

    }


    public List<Passenger> getNextPassenger() {

        if (shuttleModel.getPassengers()!=null &&!shuttleModel.getPassengers().isEmpty()) {

            distanceCalculator();
            return shuttleModel.getPassengers().stream()
                    .min(Comparator.comparing(Passenger::getDistance))
                    .map(minPassenger -> shuttleModel.getPassengers().stream()
                            .filter(p -> p.getDistance() == minPassenger.getDistance())
                            .collect(Collectors.toList()))
                    .orElse(List.of());
        }
        return null;
    }



    public void savePassengersToDb(List<Passenger> passengers) {
        try{
            for (Passenger passenger : passengers) {
                passenger.setDateTime(LocalDateTime.now());
            }
            passengersRepo.saveAll(passengers);
        }catch (Exception e){
           log.error(e.getMessage());

        }
    }

    public void updatePassengersAfterDropOff(List<Passenger> passengerList) {


        shuttleModel.setPassengers(shuttleModel.getPassengers().stream()
                        .filter(p -> !passengerList.contains(p))
                        .collect(Collectors.toList()));


    }

    @Override
    public List<Passenger> mergePassengers(List<Passenger> passengers, List<Passenger> passengersRequest) {
        if(passengers!=null && !passengers.isEmpty()) {
            if(passengersRequest!=null && !passengersRequest.isEmpty()) {
                for (Passenger passenger : passengersRequest) {
                    if(!passengers.contains(passenger)) {
                        passengers.add(passenger);
                    }
                }
            }
        }
        return passengers;
    }

     private void distanceCalculator() {
        //calculates distance between current location of shuttle to passengers location

        Random random = new Random();
        for (Passenger passenger : shuttleModel.getPassengers()) {
            passenger.setDistance(random.nextFloat(10));
        }
    }
}
