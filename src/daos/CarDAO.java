package daos;

import models.Car;

import java.util.List;

public interface CarDAO {
    public List<Car> findAll();
    public Car findById(int id);
    public List<Car> findByOwnerId(int ownerId);
    public boolean insert(Car car);
    public boolean update(Car car);
    public void delete(int id);
}
