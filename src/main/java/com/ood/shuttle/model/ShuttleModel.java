package com.ood.shuttle.model;

import com.ood.shuttle.BO.ShuttleObserver;
import com.ood.shuttle.entity.Passenger;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum ShuttleModel {One;
    private static final Logger log = LoggerFactory.getLogger(ShuttleModel.class);
    //assuming initial stop college place is at 0,0
    private double longitude;
    private double latitude;

    private List<Passenger> passengers;

    private List<ShuttleObserver> observers ;

    ShuttleModel() {

        this.longitude = 0;
        this.latitude = 0;
        this.passengers = new ArrayList<>();
        this.observers = new ArrayList<>();
    }


    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
        notifyObservers();
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
        notifyObservers();
    }

    // Set location and trigger notifications
    public void setLocation(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        notifyObservers();

    }


    // Notify all registered observers of location changes
    public void notifyObservers() {
        log.info("start of notify observers");
        for (ShuttleObserver observer : observers) {
            observer.updateShuttle(ShuttleModel.One);
        }
        log.info("end of notify observers");
    }


    public void addObserver(ShuttleObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ShuttleObserver observer) {
        observers.remove(observer);
    }


}
