package com.ood.shuttle.BO.impl;

import com.ood.shuttle.BO.ShuttleObserver;
import com.ood.shuttle.entity.ShuttleLocation;
import com.ood.shuttle.model.ShuttleModel;
import com.ood.shuttle.repo.ShuttleLocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class LocationSaver implements ShuttleObserver {

    @Autowired
    ShuttleLocationRepo shuttleLocationRepo;

    @Override
    public void updateShuttle(ShuttleModel shuttle) {
        saveLocationToDB(shuttle);

    }

    public void saveLocationToDB(ShuttleModel shuttle) {

        ShuttleLocation shuttleLastLocation = shuttleLocationRepo.findLastValueByShuttleId(shuttle.ordinal());

        if (shuttleLastLocation == null || shuttleLastLocation.getLongitude() != shuttle.getLongitude()
                || shuttleLastLocation.getLatitude() != shuttle.getLatitude()) {
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


    private ShuttleLocation getShuttleLocationModel(ShuttleModel shuttle, ShuttleLocation lastLocation) {

        ShuttleLocation shuttleLocation=shuttleLocationRepo.findByIdAndShuttleId((long) getId(lastLocation),shuttle.ordinal());

        shuttleLocation =shuttleLocation!=null?shuttleLocation: new ShuttleLocation();
        shuttleLocation.setId(getId(lastLocation));
        shuttleLocation.setLongitude(shuttle.getLongitude());
        shuttleLocation.setLatitude(shuttle.getLatitude());
        shuttleLocation.setShuttleId(shuttle.ordinal());
        shuttleLocation.setDateTime(LocalDateTime.now());

        return shuttleLocation;
    }
}
