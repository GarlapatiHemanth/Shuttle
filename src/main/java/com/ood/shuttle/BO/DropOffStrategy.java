package com.ood.shuttle.BO;

import com.ood.shuttle.entity.Passenger;
import com.ood.shuttle.service.ShuttleService;

import java.util.List;

public interface DropOffStrategy {


    List<Passenger> reorderPassengerList(ShuttleService shuttleService);

    List<Passenger> getNextPassenger(ShuttleService shuttleService);
}
