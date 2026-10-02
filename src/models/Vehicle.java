package models;

import java.time.LocalDateTime;

public abstract class Vehicle  {
    private final int id;
    private String brand;
    private String model;
    private String color;
    private int year;

    private double totalKm;
    private double currentSpeedInKph;
    private final LocalDateTime registeredAt;
    private LocalDateTime lastSpeedChangeAt;

    private final Integer ownerId;

    public Vehicle(int id, String brand, String model, String color, int year, Integer ownerId) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.year = year;
        this.registeredAt = LocalDateTime.now();
        this.ownerId = ownerId;
    }

    public abstract String getVehicleModel();
    protected abstract void drive();

    public void accelerate(double acceleration) {
        if (acceleration <= 0) {
            System.out.println("Acceleration must be greater than 0");
            return;
        }
        drive();

        currentSpeedInKph += acceleration;
        lastSpeedChangeAt = LocalDateTime.now();

        System.out.println("Current speed: " + currentSpeedInKph + " km/h");
    }

    public void brake(double percentage) {
        if (percentage <= 0 || percentage > 100) {
            System.out.println("Percentage must be between 0 and 100");
            return;
        }

        drive();
        currentSpeedInKph -= currentSpeedInKph * percentage/100;
        if (currentSpeedInKph < 0) {
            currentSpeedInKph = 0;
        }
        lastSpeedChangeAt = LocalDateTime.now();

        System.out.println("Current speed: " + currentSpeedInKph + " km/h");
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(double totalKm) {
        this.totalKm = totalKm;
    }

    public double getCurrentSpeedInKph() {
        return currentSpeedInKph;
    }

    public void setCurrentSpeedInKph(double speedInKmph) {
        this.currentSpeedInKph = speedInKmph;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public LocalDateTime getLastSpeedChangeAt() {
        return lastSpeedChangeAt;
    }

    public void setLastSpeedChangeAt(LocalDateTime lastSpeedChangeAt) {
        this.lastSpeedChangeAt = lastSpeedChangeAt;
    }

    public Integer getOwnerId() {
        return ownerId;
    }
}
