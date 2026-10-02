package models;

import enums.Gender;

public class Passenger {
    private int id;
    private String name;
    private int age;
    private Gender gender;
    private Integer vehicleId;
    private String vehicleModel;

    public Passenger() {
    }

    public Passenger(int id, String name, int age, Gender gender, Integer vehicleId, String model) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        if (vehicleId != null) {
            this.vehicleId = vehicleId;
        }
        if (model != null) {
            this.vehicleModel = model;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String display() {
        return " id: " + id +
                "\n name: " + name +
                "\n age: " + age +
                "\n gender: " + gender +
                "\n vehicleId: " + vehicleId +
                "\n vehicleModel: " + vehicleModel;
    }
}
