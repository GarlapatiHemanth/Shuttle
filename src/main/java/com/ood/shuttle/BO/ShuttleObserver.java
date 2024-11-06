package com.ood.shuttle.BO;


import com.ood.shuttle.model.ShuttleModel;



public interface ShuttleObserver {

    default void updateShuttle(ShuttleModel shuttle){}

}
