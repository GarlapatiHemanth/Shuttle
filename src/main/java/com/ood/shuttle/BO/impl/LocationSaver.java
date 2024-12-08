package com.ood.shuttle.BO.impl;

import com.ood.shuttle.BO.ShuttleObserver;
import com.ood.shuttle.entity.ShuttleLocation;
import com.ood.shuttle.repo.ShuttleLocationRepo;
import com.ood.shuttle.service.ShuttleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;


@Component
public class LocationSaver implements ShuttleObserver {

    @Autowired
    ShuttleLocationRepo shuttleLocationRepo;

    @Override
    public void updateShuttle(ShuttleService shuttle) {
        saveLocationToDB(shuttle);

    }

    public void saveLocationToDB(ShuttleService shuttle) {

        ShuttleLocation shuttleLastLocation = shuttleLocationRepo.findLastValue();

        if (shuttleLastLocation == null || shuttleLastLocation.getLongitude() != shuttle.getCurrentLongitude()
                || shuttleLastLocation.getLatitude() != shuttle.getCurrentLatitude()) {
            shuttleLocationRepo.save(getShuttleLocationModel(shuttle, shuttleLastLocation));
        }
    }

    public int getId(ShuttleLocation lastLocation) {

        if (lastLocation != null) {

            int id=(int) lastLocation.getId();
            return (id+1)%3;

        }else{
            return 0;
        }


    }


    private ShuttleLocation getShuttleLocationModel(ShuttleService shuttle, ShuttleLocation lastLocation) {

        ShuttleLocation shuttleLocation=shuttleLocationRepo.findById((long) getId(lastLocation)).isPresent()?
                shuttleLocationRepo.findById((long) getId(lastLocation)).get():null;

        shuttleLocation =shuttleLocation!=null?shuttleLocation: new ShuttleLocation();

        shuttleLocation.setId(getId(lastLocation));
        shuttleLocation.setLongitude(shuttle.getCurrentLongitude());
        shuttleLocation.setLatitude(shuttle.getCurrentLatitude());
        shuttleLocation.setDateTime(LocalDateTime.now());

        return shuttleLocation;
    }
}
