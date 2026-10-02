package daos;

import models.Car;
import models.Passenger;

import java.util.List;

public interface PassengersDAO {
    public List<Passenger> findAll();
    public Passenger findById(int id);
    public List<Passenger> findAllByGenericId(int id, String model);
    public void insert(Passenger passenger);
    public void update(Passenger passenger);
    public void delete(int id);
}
