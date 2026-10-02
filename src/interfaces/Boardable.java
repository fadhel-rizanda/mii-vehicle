package interfaces;

import models.Passenger;

import java.util.List;

public interface Boardable {
    void addPassenger(Passenger owner, Passenger passenger);
    void removePassenger(Passenger owner, Passenger passenger);
    List<Passenger> getPassengers() ;
}
