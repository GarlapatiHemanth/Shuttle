package com.ood.shuttle.BO.impl;

import com.ood.shuttle.BO.DropOffStrategy;
import com.ood.shuttle.entity.Passenger;
import com.ood.shuttle.service.ShuttleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
@Profile("default")
public class SDStrategy implements DropOffStrategy {

    private static final Logger log = LoggerFactory.getLogger(SDStrategy.class);

    @Override
    public List<Passenger> fetchNextPassengers(ShuttleService shuttleService) {
        log.info("Fetching next passengers");

        if (shuttleService.getPassengers()!=null &&!shuttleService.getPassengers().isEmpty()) {

            distanceCalculator(shuttleService);
            return shuttleService.getPassengers().stream()
                    .min(Comparator.comparing(Passenger::getDistance))
                    .map(minPassenger -> shuttleService.getPassengers().stream()
                            .filter(p -> p.getDistance() == minPassenger.getDistance())
                            .collect(Collectors.toList()))
                    .orElse(List.of());
        }
        return null;
    }

    private void distanceCalculator(ShuttleService shuttleService) {
        //calculates distance between current location of shuttle to passengers location

        Random random = new Random();
        for (Passenger passenger : shuttleService.getPassengers()) {
            passenger.setDistance(random.nextFloat(10));
        }
    }
}
