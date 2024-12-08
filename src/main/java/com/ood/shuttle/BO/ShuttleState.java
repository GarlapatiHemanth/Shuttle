package com.ood.shuttle.BO;

import com.ood.shuttle.entity.Passenger;
import com.ood.shuttle.service.ShuttleService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ShuttleState {

    ResponseEntity<Object> addPassengerToShuttle(Long suid, String address);

    ResponseEntity<Object> updateShuttleLocation(double longitude, double latitude);

    ResponseEntity<Object> fetchNextPassengerToDropOffShuttle();

    ResponseEntity<Object> dropPassengers(List<Passenger> passengerList);

    void setShuttleService(ShuttleService shuttleService);
}
