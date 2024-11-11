package com.ood.shuttle.BO.impl;

import com.ood.shuttle.entity.Passenger;
import com.ood.shuttle.model.ShuttleModel;
import com.ood.shuttle.repo.StudentRepo;
import com.ood.shuttle.BO.ShuttleInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShuttleInterfaceImpl implements ShuttleInterface {

    private static final Logger log = LoggerFactory.getLogger(ShuttleInterfaceImpl.class);
    @Autowired
    StudentRepo studentRepo;

    @Override
    public boolean checkPassenger(Long suid) {
        try{
            return studentRepo.existsById(suid);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void addPassenger(Long suid, String address) {

        try{

            ShuttleModel.One.addPassenger(getPassenger(suid,address));

        }catch (Exception e){
            log.error(e.getMessage());
        }

    }

    private Passenger getPassenger(Long suid, String address) {

        Passenger passenger = new Passenger();
        passenger.setSuid(suid);
        passenger.setAddress(address);
        passenger.setShuttleId(ShuttleModel.One.ordinal());
        return passenger;

    }
}
