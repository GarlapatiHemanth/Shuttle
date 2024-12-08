package com.ood.shuttle.BO;

import com.ood.shuttle.service.ShuttleService;


public interface ShuttleObserver {

    default void updateShuttle(ShuttleService shuttle){}

}
