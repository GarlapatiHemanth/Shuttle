package com.ood.shuttle.BO.impl;

import com.ood.shuttle.BO.DropOffInterface;
import com.ood.shuttle.BO.DropOffStrategy;
import com.ood.shuttle.BO.ShuttleObserver;
import com.ood.shuttle.entity.Passenger;
import com.ood.shuttle.service.ShuttleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

import java.util.List;


@Component
public class DropOffHandler implements ShuttleObserver,DropOffInterface {

    private static final Logger log = LoggerFactory.getLogger(DropOffHandler.class);

    private ShuttleService shuttleService;

    @Autowired
    DropOffStrategy dropOffStrategy;

    @Override
    public void updateShuttle(ShuttleService shuttle) {
        this.shuttleService = shuttle;
        updateShuttlePassengerList();
    }


    private void updateShuttlePassengerList() {
        shuttleService.setPassengers(dropOffStrategy.reorderPassengerList(shuttleService));
    }


    @Override
    public List<Passenger> getNextPassenger() {
        return dropOffStrategy.getNextPassenger(shuttleService);
    }

    @Override
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
