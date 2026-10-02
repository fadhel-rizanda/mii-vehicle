package models;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class MotorVehicle extends Vehicle {
    private final String transmission;
    private boolean isEngineOn;
    private double currentFuelInL;
    private final double tankCapacityInL;
    private double fuelConsumptionInKpl;

    public MotorVehicle(int id, String brand, String model, String color, int year, Integer ownerId,
                        double tankCapacityInL, double fuelConsumptionInKpl, String transmission) {
        super(id, brand, model, color, year, ownerId);
        if (tankCapacityInL <= 0 || fuelConsumptionInKpl <= 0) {
            throw new IllegalArgumentException("tankCapacity and fuelConsumption must be > 0");
        }
        this.tankCapacityInL = tankCapacityInL;
        this.fuelConsumptionInKpl = fuelConsumptionInKpl;
        this.transmission = transmission;
    }

    public MotorVehicle(int id, String brand, String model, String color, int year, Integer ownerId,
                        String transmission, double tankCapacityInL, boolean isEngineOn, double currentFuelInL, double fuelConsumptionInKpl) {
        super(id, brand, model, color, year, ownerId);
        this.transmission = transmission;
        this.tankCapacityInL = tankCapacityInL;
        this.isEngineOn = isEngineOn;
        this.currentFuelInL = currentFuelInL;
        this.fuelConsumptionInKpl = fuelConsumptionInKpl;
    }

    public abstract void startEngine(Passenger owner);
    public abstract void stopEngine();

    @Override
    public void accelerate(double acceleration) {
        if (!isEngineOn) {
            System.out.println("Engine is off");
            return;
        }
        super.accelerate(acceleration);
    }

    @Override
    protected void drive() {
        if (getLastSpeedChangeAt() == null) {
            setLastSpeedChangeAt(LocalDateTime.now());
            return;
        }

        double hours = Duration
                .between(getLastSpeedChangeAt(), LocalDateTime.now())
                .toSeconds() / 3600.0;

        if (!isEngineOn) {
            System.out.println("Engine is off");
            return;
        }

        if (getCurrentSpeedInKph() <= 0) {
            System.out.println("Vehicle is not moving");
            return;
        }

        double distanceCovered = getCurrentSpeedInKph() * hours;
        double fuelNeeded = distanceCovered / fuelConsumptionInKpl;

        if (fuelNeeded > currentFuelInL) {
            double possibleDistance = currentFuelInL * fuelConsumptionInKpl;
            currentFuelInL = 0;
            setCurrentSpeedInKph(0);
            setTotalKm(getTotalKm() + possibleDistance);
            System.out.println("Out of fuel after " + possibleDistance + " km");
            stopEngine();
            return;
        }

        setTotalKm(getTotalKm() + distanceCovered);
        currentFuelInL -= fuelNeeded;

        System.out.println("Speed: " + getCurrentSpeedInKph() + " km/h");
        System.out.println("Distance: " + distanceCovered + " km");
        System.out.println("Fuel remaining: " + currentFuelInL + " l");
    }

    public void refuel(double amount) {
        if (amount <= 0) {
            System.out.println("Amount must be greater than 0");
            return;
        }

        if (getCurrentSpeedInKph() > 0) {
            System.out.println("Can't refuel, car is still moving");
            return;
        }

        if (isEngineOn) {
            System.out.println("Turn off the engine first");
            return;
        }

        currentFuelInL += amount;
        if (currentFuelInL > tankCapacityInL) {
            System.out.println("Fuel overloaded by: " + (currentFuelInL - tankCapacityInL));
            currentFuelInL = tankCapacityInL;
        }
        System.out.println("Current fuel: " + currentFuelInL + " l");
    }

    public String getTransmission() {
        return transmission;
    }

    public double getCurrentFuelInL() {
        return currentFuelInL;
    }

    public boolean isEngineOn() {
        return isEngineOn;
    }

    public void setEngineOn(boolean engineOn) {
        this.isEngineOn = engineOn;
    }

    public double getTankCapacityInL() {
        return tankCapacityInL;
    }

    public double getFuelConsumptionInKpl() {
        return fuelConsumptionInKpl;
    }

    public void setFuelConsumptionInKpl(double fuelConsumptionInKpl) {
        this.fuelConsumptionInKpl = fuelConsumptionInKpl;
    }
}
