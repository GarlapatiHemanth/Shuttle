package com.ood.shuttle.repo;

import com.ood.shuttle.entity.Passenger;
import org.springframework.data.repository.CrudRepository;

public interface PassengersRepo extends CrudRepository<Passenger, Long> {
}
