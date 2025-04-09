package com.ood.shuttle.controller;

import com.ood.shuttle.entity.Passenger;
import com.ood.shuttle.service.ShuttleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ip")
public class ShuttleController {

    private static final Logger log = LoggerFactory.getLogger(ShuttleController.class);
    @Autowired
    ShuttleService shuttleService;

    @PostMapping("/addPassenger")
    public ResponseEntity<Object> addPassenger(@RequestParam(name="suid") Long suid,@RequestParam(name="address") String address){

        log.info("Adding passenger with suid {} and address {}", suid, address);
        return shuttleService.addPassengerToShuttle(suid,address);
    }

    @PostMapping("/shuttleLocation")
    public ResponseEntity<Object> updateShuttleLocation(@RequestParam double longitude, @RequestParam double latitude) {

        log.info("Update shuttle location");
        return shuttleService.updateShuttleLocation(longitude,latitude);
    }

    //below two are additional end points but not necessarily required to be called. Since i am updating the passenger list.
    @GetMapping("/fetchNextPassenger")
    public ResponseEntity<Object> fetchNextPassenger() {
        log.info("Fetching next passenger");

        return shuttleService.fetchNextPassengerToDropOffShuttle();
    }

    //driver can pass the list of students getting dropped or just call this end point without any parameter
    @PostMapping("/dropOff")
    public ResponseEntity<Object> dropOff(@RequestBody(required = false) List<Passenger> passengers)  {
        log.info("Drop off shuttle");

        return shuttleService.dropPassengers(passengers);
    }


}
