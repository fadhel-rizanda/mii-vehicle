package models;

import interfaces.Boardable;
import interfaces.Honkable;
import interfaces.Lockable;

import java.util.List;

public class Car extends MotorVehicle implements Lockable, Honkable, Boardable {
    private final int totalDoors;
    private boolean isDoorsLocked;
    private final int totalSeat;
    private List<Passenger> passengers;

    public Car(int id, String brand, String model, String color, int year, Integer ownerId,
               double tankCapacityInL, double fuelConsumptionInKpl, String transmission, int totalDoors, int totalSeat) {
        super(id, brand, model, color, year, ownerId,
                tankCapacityInL, fuelConsumptionInKpl, transmission);
        if (totalDoors <= 0 || totalSeat <= 0) {
            throw new IllegalArgumentException("doorsCount or totalSeat must be > 0");
        }
        this.totalDoors = totalDoors;
        this.totalSeat = totalSeat;
        this.passengers = new java.util.ArrayList<>();
    }

    public Car(int id, String brand, String model, String color, int year, Integer ownerId, double tankCapacityInL, double fuelConsumptionInKpl, String transmission, int totalDoors, int totalSeat, boolean isDoorsLocked, List<Passenger> passengers) {
        super(id, brand, model, color, year, ownerId, tankCapacityInL, fuelConsumptionInKpl, transmission);
        this.totalDoors = totalDoors;
        this.totalSeat = totalSeat;
        this.isDoorsLocked = isDoorsLocked;
        this.passengers = passengers;
    }

    public Car(int id, String brand, String model, String color, int year, Integer ownerId, String transmission, double tankCapacityInL, boolean isEngineOn, double currentFuelInL, double fuelConsumptionInKpl, int totalDoors, int totalSeat, boolean isDoorsLocked, List<Passenger> passengers) {
        super(id, brand, model, color, year, ownerId, transmission, tankCapacityInL, isEngineOn, currentFuelInL, fuelConsumptionInKpl);
        this.totalDoors = totalDoors;
        this.totalSeat = totalSeat;
        this.isDoorsLocked = isDoorsLocked;
        this.passengers = passengers;
    }

    @Override
    public void addPassenger(Passenger owner, Passenger passenger) {
        if (getOwnerId() == null || getOwnerId() != owner.getId()) {
            System.out.println("Only the owner can add passengers");
            return;
        }
        if(getCurrentSpeedInKph() > 0) {
            System.out.println("Cannot add passenger while the car is moving");
            return;
        }
        if(isDoorsLocked) {
            System.out.println("Cannot add passenger, doors are locked");
            return;
        }
        if (passengers != null && passengers.size() >= totalSeat) {
            System.out.println("Cannot add more passengers, car is full");
            return;
        }
        if (passengers == null) {
            passengers = new java.util.ArrayList<>();
        }
        passengers.add(passenger);
        System.out.println("Passenger added: " + passenger.getName());
    }

    @Override
    public void removePassenger(Passenger owner, Passenger passenger) {
        if (getOwnerId() == null || getOwnerId() != owner.getId()) {
            System.out.println("Only the owner can remove passengers");
            return;
        }
        if(getCurrentSpeedInKph() > 0) {
            System.out.println("Cannot remove passenger while the car is moving");
            return;
        }
        if(isDoorsLocked) {
            System.out.println("Cannot remove passenger, doors are locked");
            return;
        }

        if (passengers != null && passengers.remove(passenger)) {
            System.out.println("Passenger removed: " + passenger.getName());
        } else {
            System.out.println("Passenger not found: " + passenger.getName());
        }
    }

    @Override
    public List<Passenger> getPassengers() {
        return passengers;
    }

    @Override
    public void startEngine(Passenger owner) {
        if (getOwnerId() != owner.getId()) {
            System.out.println("Only the owner can start the engine");
            return;
        }
        if (isEngineOn()) {
            System.out.println("Engine already on");
            return;
        }

        if (getCurrentFuelInL() <= 0) {
            System.out.println("No fuel");
            return;
        }

        setEngineOn(true);
        System.out.println("Engine started");
    }

    @Override
    public void stopEngine() {
        if (getCurrentSpeedInKph() > 0) {
            System.out.println("Stop the vehicle first");
            return;
        }

        setEngineOn(false);
        System.out.println("Engine stopped");
    }

    @Override
    public void honk() {
        System.out.println("Beep beep!");
    }

    public void honk(String custom) {
        System.out.println(custom);
    }

    public int getTotalDoors() {
        return totalDoors;
    }

    public boolean isDoorsLocked() {
        return isDoorsLocked;
    }

    public int getTotalSeat() {
        return totalSeat;
    }

    @Override
    public void lock() {
        isDoorsLocked = true;
        System.out.println("Doors locked");
    }

    @Override
    public void unlock() {
        isDoorsLocked = false;
        System.out.println("Doors unlocked");
    }

    @Override
    public boolean isLocked() {
        return this.isDoorsLocked;
    }

    @Override
    public String getVehicleModel() {
        return "Car";
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public String display() {
        return " id: " + getId() +
                "\n brand: '" + getBrand() + '\'' +
                "\n model: '" + getModel() + '\'' +
                "\n color: '" + getColor() + '\'' +
                "\n year: " + getYear() +
                "\n ownerId: " + getOwnerId() +
                "\n totalKm: " + getTotalKm() +
                "\n currentSpeedInKph: " + getCurrentSpeedInKph() +
                "\n registeredAt: " + getRegisteredAt() +
                "\n lastSpeedChangeAt: " + getLastSpeedChangeAt() +
                "\n transmission: '" + getTransmission() + '\'' +
                "\n isEngineOn: " + isEngineOn() +
                "\n currentFuelInL: " + getCurrentFuelInL() +
                "\n tankCapacityInL: " + getTankCapacityInL() +
                "\n fuelConsumptionInKpl: " + getFuelConsumptionInKpl() +
                "\n totalDoors: " + totalDoors +
                "\n isDoorsLocked: " + isDoorsLocked +
                "\n totalSeat: " + totalSeat +
                "\n passengers: " + passengers;
    }
}
