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
import java.util.List;

@Component
@Profile("FCFS")
public class FCFSStrategy implements DropOffStrategy {

    private static final Logger log = LoggerFactory.getLogger(FCFSStrategy.class);

    @Override
    public List<Passenger> fetchNextPassengers(ShuttleService shuttleService) {

        log.info("Fetching next passengers");

        List<Passenger> first = new ArrayList<>();
        first.add(shuttleService.getPassengers().get(0));
        return first;
    }
}
