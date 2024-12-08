package com.ood.shuttle.service;

import com.ood.shuttle.BO.ShuttleInterface;
import com.ood.shuttle.BO.ShuttleObserver;
import com.ood.shuttle.BO.ShuttleState;
import com.ood.shuttle.entity.Passenger;
import com.ood.shuttle.entity.Student;
import com.ood.shuttle.repo.StudentRepo;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("singleton")//default is singleton but to show it explicitly
@Getter
@Setter
public class ShuttleService implements ShuttleInterface {

    private static final Logger log = LoggerFactory.getLogger(ShuttleService.class);

    @Qualifier("dropOffHandler")
    @Autowired
    ShuttleObserver dropOffObserver;


    @Qualifier("locationSaver")
    @Autowired
    ShuttleObserver locationObserver;

    @Autowired
    StudentRepo studentRepo;

    //considered college place as initial

    @Value("${collegePlace.Latitude}")
    private double collegePlaceLatitude;

    @Value("${collegePlace.Longitude}")
    private double collegePlaceLongitude;

    private double currentLongitude;
    private double currentLatitude;

    private ShuttleState currentState;

    ShuttleState idleState;

    ShuttleState runningState;


    private List<Passenger> passengers;

    private List<ShuttleObserver> observers ;

    @Autowired
    public ShuttleService(@Qualifier("idleState") ShuttleState idleState,
                          @Qualifier("runningState") ShuttleState runningState) {
        this.idleState = idleState;
        this.runningState = runningState;
    }

    @PostConstruct
    public void initializeShuttle() {

        this.currentLongitude =collegePlaceLongitude;
        this.currentLatitude =collegePlaceLatitude;
        this.idleState.setShuttleService(this);
        this.runningState.setShuttleService(this);
        this.currentState=idleState;
        this.passengers = new ArrayList<>();

        if (this.observers==null || this.observers.isEmpty()){
            this.observers = new ArrayList<>();
            this.addObserver(dropOffObserver);
            this.addObserver(locationObserver);
        }
    }

    //main start

    public ResponseEntity<Object> addPassengerToShuttle(Long suid, String address) {
        if(currentState==idleState){
            setCurrentStateToRunningState();
        }
        return currentState.addPassengerToShuttle(suid, address);
    }


    public ResponseEntity<Object> updateShuttleLocation(double longitude, double latitude) {

        if(longitude == collegePlaceLongitude && latitude == collegePlaceLatitude &&
                this.currentLongitude!=collegePlaceLongitude && this.currentLatitude!=collegePlaceLatitude
        && currentState==runningState){

           setCurrentStateToIdleState();
        }
        return currentState.updateShuttleLocation(longitude, latitude);
    }


    public ResponseEntity<Object> fetchNextPassengerToDropOffShuttle() {
        return currentState.fetchNextPassengerToDropOffShuttle();
    }


    public ResponseEntity<Object> dropPassengers(List<Passenger> passengerList) {
        return currentState.dropPassengers(passengerList);
    }

    @Override
    public boolean checkPassenger(Long suid) {
        try{
            return studentRepo.existsById(suid);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void addPassenger(Long suid, String address) {

        try{

            this.addPassenger(getPassenger(suid,address));

        }catch (Exception e){
            log.error(e.getMessage());
        }

    }

    //adapter
    private Passenger getPassenger(Long suid, String address) {

        Student student= studentRepo.findBySuid(suid);

        Passenger passenger = new Passenger();
        passenger.setStudent(student);
        passenger.setAddress(address);
        return passenger;

    }

    //main end

    private void setCurrentStateToRunningState() {
        currentState=runningState;
    }

    private void setCurrentStateToIdleState() {
        currentState=idleState;
    }

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
        notifyObservers();
    }

    // Set location and trigger notifications
    public void setLocation(double longitude, double latitude) {
        this.currentLongitude = longitude;
        this.currentLatitude = latitude;
        notifyObservers();

    }


    // Notify all registered observers of location changes
    public void notifyObservers() {
        log.info("start of notify observers");
        for (ShuttleObserver observer : observers) {
            observer.updateShuttle(this);
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
