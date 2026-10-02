package models;

import java.time.Duration;
import java.time.LocalDateTime;

public class Bicycle extends Vehicle {
    private final int totalGears;
    private int currentGear;

    public Bicycle(int id, String brand, String model, String color, int year, Integer ownerId,
                   int totalGears) {
        super(id, brand, model, color, year, ownerId);
        this.totalGears = totalGears;
        this.currentGear = 1;
    }

    @Override
    public void drive() {
        if (getLastSpeedChangeAt() == null) {
            setLastSpeedChangeAt(LocalDateTime.now());
            return;
        }

        double hours = Duration
                .between(getLastSpeedChangeAt(), LocalDateTime.now())
                .toSeconds() / 3600.0;

        if (getCurrentSpeedInKph() <= 0) {
            System.out.println("Vehicle is not moving");
            return;
        }

        double distanceCovered = getCurrentSpeedInKph() * hours;
        setTotalKm(getTotalKm() + distanceCovered);

        System.out.println("Speed: " + getCurrentSpeedInKph() + " km/h");
        System.out.println("Distance: " + distanceCovered + " km");
    }

    public void shiftUp() {
        if (currentGear >= totalGears) {
            System.out.println("Already at highest gear");
            return;
        }
        currentGear++;
        System.out.println("Gear: " + currentGear);
    }

    public void shiftDown() {
        if (currentGear <= 1) {
            System.out.println("Already at lowest gear");
            return;
        }
        currentGear--;
        System.out.println("Gear: " + currentGear);
    }

    public int getTotalGears() {
        return totalGears;
    }

    public int getCurrentGear() {
        return currentGear;
    }

    @Override
    public String getVehicleModel() {
        return "Bicycle";
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
                "\n totalGears: " + totalGears +
                "\n currentGear: " + currentGear +
                '\n';
    }
}
