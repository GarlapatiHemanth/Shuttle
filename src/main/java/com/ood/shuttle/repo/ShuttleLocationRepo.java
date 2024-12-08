package com.ood.shuttle.repo;


import com.ood.shuttle.entity.ShuttleLocation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface ShuttleLocationRepo extends CrudRepository<ShuttleLocation, Long>  {

    @Query("SELECT sl FROM ShuttleLocation sl ORDER BY sl.dateTime DESC LIMIT 1")
    ShuttleLocation findLastValue();

    Optional<ShuttleLocation> findById(Long id);


}
