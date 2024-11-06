package com.ood.shuttle.controller;

import com.ood.shuttle.entity.Passenger;
import com.ood.shuttle.service.ShuttleServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableScheduling
@RequestMapping("/ip")
public class ShuttleController {

    private static final Logger log = LoggerFactory.getLogger(ShuttleController.class);
    @Autowired
    ShuttleServices shuttleServices;

    @GetMapping("/addPassenger")
    public ResponseEntity<Object> addPassenger(@RequestParam(name="suid") Long suid,@RequestParam(name="address") String address){


        return shuttleServices.addPassengerToShuttle(suid,address);
    }

    @GetMapping("/shuttleLocation")
    public ResponseEntity<Object> updateShuttleLocation(@RequestParam double longitude, @RequestParam double latitude) {

        return shuttleServices.updateShuttleLocation(longitude,latitude);
    }

    @GetMapping("/fetchNextPassenger")
    public ResponseEntity<Object> fetchNextPassenger() {

        return shuttleServices.fetchNextPassengerToDropOffShuttle();
    }

    @PostMapping("/dropOff")
    public ResponseEntity<Object> dropOff(@RequestBody(required = false) List<Passenger> passengers)  {
        log.info("Drop off shuttle");

        return shuttleServices.dropPassengers(passengers);
    }


}
