package com.ood.shuttle.BO;


import com.ood.shuttle.entity.Passenger;

import java.util.List;

//template
public interface DropOffInterface {

    default void handleDropOff(List<Passenger> passengerList){

            List<Passenger> passengers = getNextPassenger();
            passengers=mergePassengers(passengers,passengerList);
            if (passengers!=null && !passengers.isEmpty()) {

                savePassengersToDb(passengers);
                updatePassengersAfterDropOff(passengers);

            }


    }
    List<Passenger> mergePassengers(List<Passenger> passengers, List<Passenger> passengersRequest);

    List<Passenger> getNextPassenger();

    void savePassengersToDb(List<Passenger> passengers);

    void updatePassengersAfterDropOff(List<Passenger> passengerList);
}
