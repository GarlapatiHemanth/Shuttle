package com.ood.shuttle.BO.impl;

import com.ood.shuttle.BO.DropOffStrategy;
import com.ood.shuttle.entity.Passenger;
import com.ood.shuttle.service.ShuttleService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("FCFS")
public class FCFSStrategy implements DropOffStrategy {


    @Override
    public List<Passenger> reorderPassengerList(ShuttleService shuttleService) {
        return shuttleService.getPassengers();
    }

    @Override
    public List<Passenger> getNextPassenger(ShuttleService shuttleService) {
        return List.of(shuttleService.getPassengers().get(0));
    }
}
