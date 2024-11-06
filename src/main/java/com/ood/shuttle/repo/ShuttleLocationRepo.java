package com.ood.shuttle.repo;


import com.ood.shuttle.entity.ShuttleLocation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface ShuttleLocationRepo extends CrudRepository<ShuttleLocation, Long>  {

    @Query("SELECT sl FROM ShuttleLocation sl WHERE sl.shuttleId = :shuttleId ORDER BY sl.dateTime DESC LIMIT 1")
    ShuttleLocation findLastValueByShuttleId(int shuttleId);

    ShuttleLocation findByIdAndShuttleId(Long id, Integer shuttleId);


}
