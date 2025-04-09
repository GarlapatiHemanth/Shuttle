package com.ood.shuttle.BO.impl;

import com.ood.shuttle.BO.DropOffStrategy;
import com.ood.shuttle.entity.Passenger;
import com.ood.shuttle.service.ShuttleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Component
@Profile("default")
public class SDStrategy implements DropOffStrategy {

    private static final Logger log = LoggerFactory.getLogger(SDStrategy.class);

    @Override
    public List<Passenger> reorderPassengerList(ShuttleService shuttleService) {

        List<Passenger> passengers = distanceCalculator(shuttleService.getPassengers());

        passengers.sort(Comparator.comparing(Passenger::getDistance));

        return passengers;
    }

    @Override
    public List<Passenger> getNextPassenger(ShuttleService shuttleService) {

        List<Passenger> result = new ArrayList<>();

        for (Passenger passenger : shuttleService.getPassengers()) {
            if (passenger.getDistance() == shuttleService.getPassengers().get(0).getDistance()) {
                result.add(passenger);
            } else {
                break;
            }
        }
        return result;
    }

    private List<Passenger> distanceCalculator(List<Passenger> passengers) {
        //calculates distance between current location of shuttle to passengers location

        Random random = new Random();
        for (Passenger passenger : passengers) {
            passenger.setDistance(random.nextFloat(10));
        }
        return passengers;
    }
}
