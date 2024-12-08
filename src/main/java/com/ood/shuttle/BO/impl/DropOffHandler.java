package com.ood.shuttle.BO.impl;

import com.ood.shuttle.BO.DropOffInterface;
import com.ood.shuttle.BO.DropOffStrategy;
import com.ood.shuttle.BO.ShuttleObserver;
import com.ood.shuttle.entity.Passenger;
import com.ood.shuttle.repo.PassengersRepo;
import com.ood.shuttle.repo.ShuttleLocationRepo;
import com.ood.shuttle.service.ShuttleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DropOffHandler implements ShuttleObserver, DropOffInterface {

    private static final Logger log = LoggerFactory.getLogger(DropOffHandler.class);


    private ShuttleService shuttleService;

    @Autowired
    ShuttleLocationRepo shuttleLocationRepo;

    @Autowired
    PassengersRepo passengersRepo;

    @Autowired
    DropOffStrategy dropOffStrategy;


    @Override
    public void updateShuttle(ShuttleService shuttle) {

        this.shuttleService = shuttle;

    }

//todo
    public List<Passenger> getNextPassenger() {

        return dropOffStrategy.fetchNextPassengers(shuttleService);
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


        shuttleService.setPassengers(shuttleService.getPassengers().stream()
                        .filter(p -> !passengerList.contains(p))
                        .collect(Collectors.toList()));


    }

    @Override
    public List<Passenger> mergePassengers(List<Passenger> passengers, List<Passenger> passengersRequest) {
        if(passengers!=null && !passengers.isEmpty() && passengersRequest!=null && !passengersRequest.isEmpty()) {

                for (Passenger passenger : passengersRequest) {
                    if(!passengers.contains(passenger)) {
                        passengers.add(passenger);
                    }
                }
            }

        return passengers;
    }
}
