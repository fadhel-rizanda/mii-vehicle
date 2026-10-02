package models;

import interfaces.Boardable;
import interfaces.Honkable;

import java.util.List;

public class Motorcycle extends MotorVehicle implements Honkable, Boardable {
    private boolean isKickstandDown;
    private List<Passenger> passengers;

    public Motorcycle(int id, String brand, String model, String color, int year, double tankCapacityInL, double fuelConsumptionInKpl, String transmission, Integer ownerId) {
        super(id, brand, model, color, year, ownerId,
                tankCapacityInL, fuelConsumptionInKpl, transmission);
        this.isKickstandDown = true;
        this.passengers = new java.util.ArrayList<>();
    }

    public Motorcycle(int id, String brand, String model, String color, int year, Integer ownerId, double tankCapacityInL, double fuelConsumptionInKpl, String transmission, boolean isKickstandDown, List<Passenger> passengers) {
        super(id, brand, model, color, year, ownerId, tankCapacityInL, fuelConsumptionInKpl, transmission);
        this.isKickstandDown = isKickstandDown;
        this.passengers = passengers;
    }

    public Motorcycle(int id, String brand, String model, String color, int year, Integer ownerId, String transmission, double tankCapacityInL, boolean isEngineOn, double currentFuelInL, double fuelConsumptionInKpl, boolean isKickstandDown, List<Passenger> passengers) {
        super(id, brand, model, color, year, ownerId, transmission, tankCapacityInL, isEngineOn, currentFuelInL, fuelConsumptionInKpl);
        this.isKickstandDown = isKickstandDown;
        this.passengers = passengers;
    }

    @Override
    public void startEngine(Passenger owner) {
        if (getOwnerId() == null || getOwnerId() != owner.getId()) {
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
        if (!isEngineOn()) {
            System.out.println("Engine already off");
            return;
        }

        if (getCurrentSpeedInKph() > 0) {
            System.out.println("Stop the vehicle first");
            return;
        }

        setEngineOn(false);
        System.out.println("Engine stopped");
    }

    @Override
    public void honk() {
        System.out.println("Tin tin!");
    }

    @Override
    public void accelerate(double acceleration) {
        if (isKickstandDown) {
            System.out.println("Raise the kickstand first");
            return;
        }
        super.accelerate(acceleration);
    }

    public void raiseKickstand() {
        isKickstandDown = false;
        System.out.println("Kickstand raised");
    }

    public void lowerKickstand() {
        if (getCurrentSpeedInKph() > 0) {
            System.out.println("Can't lower kickstand while moving");
            return;
        }

        isKickstandDown = true;
        System.out.println("Kickstand lowered");
    }

    public boolean isKickstandDown() {
        return isKickstandDown;
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
        if (passengers.size() >= 2) {
            System.out.println("Cannot add more passengers, motorcycle is full");
            return;
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
            System.out.println("Cannot add passenger while the car is moving");
            return;
        }

        if (passengers.remove(passenger)) {
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
    public String getVehicleModel() {
        return "Motorcycle";
    }

    public String display() {
        return " id: " + getId() +
                "\n brand: " + getBrand() +
                "\n model: " + getModel() +
                "\n color: " + getColor() +
                "\n year: " + getYear() +
                "\n ownerId: " + getOwnerId() +
                "\n totalKm: " + getTotalKm() +
                "\n currentSpeedInKph: " + getCurrentSpeedInKph() +
                "\n registeredAt: " + getRegisteredAt() +
                "\n lastSpeedChangeAt: " + getLastSpeedChangeAt() +
                "\n transmission: " + getTransmission() +
                "\n isEngineOn: " + isEngineOn() +
                "\n currentFuelInL: " + getCurrentFuelInL() +
                "\n tankCapacityInL: " + getTankCapacityInL() +
                "\n fuelConsumptionInKpl: " + getFuelConsumptionInKpl() +
                "\n isKickstandDown: " + isKickstandDown +
                "\n passengers: " + passengers;
    }
}
