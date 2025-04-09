package com.ood.shuttle.BO.impl;

import com.ood.shuttle.BO.ShuttleObserver;
import com.ood.shuttle.service.ShuttleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//this can be used to send notifications regarding shuttle location
@Component
public class LocationNotification implements ShuttleObserver {
    private static final Logger log = LoggerFactory.getLogger(LocationNotification.class);

    @Override
    public void updateShuttle(ShuttleService shuttle) {

    }

    private void sendNotification(ShuttleService shuttle) {

        log.info("Shuttle is at latitude {} and longitude {}", shuttle.getCurrentLatitude(), shuttle.getCurrentLongitude());

    }
}
