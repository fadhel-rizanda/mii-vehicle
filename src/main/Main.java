package main;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import daos.*;
import enums.Gender;
import models.Bicycle;
import models.Car;
import models.Passenger;
import tools.DBConnection;

public class Main {
    public static void main(String[] args) {
        DBConnection connection = new DBConnection();
        Connection con = connection.getConnection();

        PassengersDAO passengersDAO = new PassengersDAOImpl(con);
        CarDAO carDAO = new CarDAOImpl(con);
        MotorcycleDAOImpl motorcycleDAO = new MotorcycleDAOImpl(con);
        BicycleDAOImpl bicycleDAO = new BicycleDAOImpl(con);

        Scanner scan = new Scanner(System.in);
        int menu = 0;
        Passenger user = null;
        Car myCar = null;

        do {
            System.out.println("\n=== VEHICLE MANAGEMENT SYSTEM ===");
            if (user != null && myCar != null) {
                System.out.println("Welcome, " + user.getName() + "!");
                System.out.println("You are currently using: " + myCar.getModel());
                System.out.println("13. Car information");
                System.out.println("14. Start engine");
                System.out.println("15. Stop engine");
                System.out.println("16. Refuel");
                System.out.println("17. Accelerate");
                System.out.println("18. Brake");
                System.out.println("19. Honk");
                System.out.println("20. Lock doors");
                System.out.println("21. Unlock doors");
                System.out.println("22. Add passenger");
                System.out.println("23. Remove passenger");
                System.out.println("24. View passengers");
                System.out.println("25. Delete vehicle");
            } else if (user != null) {
                System.out.println("Welcome, " + user.getName() + "!");
                System.out.println("7. View my cars");
                System.out.println("8. View my motorcycles");
                System.out.println("9. View my bicycles");
                System.out.println("10. Add a new vehicle");
                System.out.println("11. Use a vehicle");
                System.out.println("12. Delete passenger");
            } else {
                System.out.println("1. View all passengers");
                System.out.println("2. View all cars");
                System.out.println("3. View all motorcycles");
                System.out.println("4. View all bicycles");
                System.out.println("5. Add a new passenger");
                System.out.println("6. Use existing passenger");
            }
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            menu = scan.nextInt();
            scan.nextLine();
            System.out.println();

            switch (menu) {
                case 1:
                    for (Passenger passenger : passengersDAO.findAll()) {
                        System.out.println("================================");
                        System.out.println(passenger.display());
                    }
                    System.out.println("================================");
                    break;
                case 2:
                    carDAO.findAll().forEach(r -> {
                        System.out.println("================================");
                        System.out.println(r.display());
                    });
                    System.out.println("================================");
                    break;
                case 3:
                    motorcycleDAO.findAll().stream().filter(p -> p.getRegisteredAt() != null).forEach(r -> {
                        System.out.println("================================");
                        System.out.println(r.display());
                    });
                    System.out.println("================================");
                    break;
                case 4:
                    for (Bicycle bicycle : bicycleDAO.findAll()) {
                        System.out.println("================================");
                        System.out.println(bicycle);
                    }
                    System.out.println("================================");
                    break;
                case 5:
                    System.out.print("Enter passenger ID: ");
                    int id = scan.nextInt();
                    scan.nextLine();
                    System.out.print("Enter passenger name: ");
                    String name = scan.nextLine();
                    System.out.print("Enter passenger age: ");
                    int age = scan.nextInt();
                    scan.nextLine();
                    System.out.print("Enter passenger gender (MALE/FEMALE): ");
                    Gender gender = Gender.valueOf(scan.nextLine().toUpperCase());

                    Passenger newPassenger = new Passenger(id, name, age, gender, null, null);
                    passengersDAO.insert(newPassenger);
                    user = newPassenger;
                    System.out.println("Passenger added successfully!");
                    break;
                case 6:
                    System.out.print("Enter passenger ID to use: ");
                    int passengerId = scan.nextInt();
                    scan.nextLine();
                    user = passengersDAO.findById(passengerId);

                    if (user != null) {
                        System.out.println("Passenger found: " + user);
                    } else {
                        System.out.println("Passenger not found.");
                    }
                    break;
                case 7:
                    if (user != null) {
                        List<Car> car = carDAO.findByOwnerId(user.getId());
                        if (car.size() <= 0) {
                            System.out.println("You have no cars.");
                        } else {
                            car.forEach(r-> {
                                System.out.println("================================");
                                System.out.println(r.display());
                            });
                            System.out.println("================================");
                        }
                    } else {
                        System.out.println("Please select a passenger first.");
                    }
                    break;
                case 10:
                    if (user != null) {
                        System.out.print("Enter vehicle type (CAR/MOTORCYCLE/BICYCLE): ");
                        String vehicleType = scan.nextLine().toUpperCase();
                        System.out.print("Enter vehicle ID: ");
                        int vehicleId = scan.nextInt();
                        scan.nextLine();
                        System.out.print("Enter vehicle brand: ");
                        String brand = scan.nextLine();
                        System.out.print("Enter vehicle model: ");
                        String model = scan.nextLine();
                        System.out.print("Enter vehicle color: ");
                        String color = scan.nextLine();
                        System.out.print("Enter vehicle year: ");
                        int year = scan.nextInt();
                        scan.nextLine();

                        switch (vehicleType) {
                            case "CAR":
                                System.out.print("Enter vehicle transmission: ");
                                String transmission = scan.nextLine();
                                System.out.print("Enter vehicle tank capacity(l): ");
                                int tankCapacity = scan.nextInt();
                                scan.nextLine();
                                System.out.print("Enter vehicle fuel consumption(kpl): ");
                                int fuelConsumption = scan.nextInt();
                                scan.nextLine();
                                System.out.print("Enter total door: ");
                                int totalDoor = scan.nextInt();
                                scan.nextLine();
                                System.out.print("Enter total seat: ");
                                int totalSeat = scan.nextInt();
                                scan.nextLine();

                                Car newCar = new Car(vehicleId, brand, model, color, year, user.getId(), transmission, tankCapacity, false, 0, fuelConsumption, totalDoor, totalSeat, false, new ArrayList<>());
                                carDAO.insert(newCar);
                                user.setVehicleId(newCar.getId());
                                user.setVehicleModel(newCar.getVehicleModel());
                                passengersDAO.update(user);
                                System.out.println("Car added successfully!");
                                break;
                            default:
                                System.out.println("Invalid vehicle type.");
                        }
                    } else {
                        System.out.println("Please select a passenger first.");
                    }
                    break;
                case 11:
                    if (user != null) {
//                        System.out.print("Enter vehicle type (CAR/MOTORCYCLE/BICYCLE): ");
                        System.out.print("Enter vehicle type (CAR): "); // baru bisa car
                        String vehicleType = scan.nextLine().toUpperCase();
                        System.out.print("Enter vehicle ID: ");
                        int vehicleId = scan.nextInt();
                        scan.nextLine();

                        switch (vehicleType) {
                            case "CAR":
                                Car carToUse = carDAO.findById(vehicleId);
                                if (carToUse != null && carToUse.getOwnerId() == user.getId()) {
                                    myCar = carToUse;
                                    System.out.println("You are now using the car: " + myCar);
                                } else {
                                    System.out.println("Car not found or you are not the owner.");
                                }
                                break;
                            default:
                                System.out.println("Invalid vehicle type.");
                        }
                    } else {
                        System.out.println("Please select a passenger first.");
                    }
                    break;
                case 12:
                    if (user != null) {
                        passengersDAO.delete(user.getId());
                        System.out.println("Passenger deleted successfully.");
                        user = null;
                    } else {
                        System.out.println("Please select a passenger first.");
                    }
                    break;
                case 13:
                    if (myCar != null) {
                        List<Passenger> passengers = passengersDAO.findAllByGenericId(myCar.getId(), myCar.getVehicleModel());
                        myCar.setPassengers(passengers);
                        System.out.println(myCar);
                    } else {
                        System.out.println("Please select a vehicle first.");
                    }
                    break;
                case 14:
                    if (user != null && myCar != null) {
                        myCar.startEngine(user);
                        carDAO.update(myCar);
                    } else {
                        System.out.println("Please select a passenger and a vehicle first.");
                    }
                    break;
                case 15:
                    if (user != null && myCar != null) {
                        myCar.stopEngine();
                        carDAO.update(myCar);
                    } else {
                        System.out.println("Please select a passenger and a vehicle first.");
                    }
                    break;
                case 16:
                    if (user != null && myCar != null) {
                        System.out.print("Enter fuel amount in liters: ");
                        double fuelAmount = Double.parseDouble(scan.nextLine());
                        myCar.refuel(fuelAmount);
                        carDAO.update(myCar);
                    } else {
                        System.out.println("Please select a passenger and a vehicle first.");
                    }
                    break;
                case 17:
                    if (user != null && myCar != null) {
                        System.out.print("Enter acceleration in km/h: ");
                        double acceleration = Double.parseDouble(scan.nextLine());
                        myCar.accelerate(acceleration);
                        carDAO.update(myCar);
                    } else {
                        System.out.println("Please select a passenger and a vehicle first.");
                    }
                    break;
                case 18:
                    if (user != null && myCar != null) {
                        System.out.print("Enter brake percentage (0-100): ");
                        double percentage = Double.parseDouble(scan.nextLine());
                        myCar.brake(percentage);
                        carDAO.update(myCar);
                    } else {
                        System.out.println("Please select a passenger and a vehicle first.");
                    }
                    break;
                case 19:
                    if (user != null && myCar != null) {
                        myCar.honk();
                    } else {
                        System.out.println("Please select a passenger and a vehicle first.");
                    }
                    break;
                case 20:
                    if (user != null && myCar != null) {
                        myCar.lock();
                        carDAO.update(myCar);
                    } else {
                        System.out.println("Please select a passenger and a vehicle first.");
                    }
                    break;
                case 21:
                    if (user != null && myCar != null) {
                        myCar.unlock();
                        carDAO.update(myCar);
                    } else {
                        System.out.println("Please select a passenger and a vehicle first.");
                    }
                    break;
                case 22:
                    if (user != null && myCar != null) {
                        System.out.print("Enter passenger ID to add: ");
                        int passengerIdToAdd = scan.nextInt();
                        scan.nextLine();
                        Passenger passengerToAdd = passengersDAO.findById(passengerIdToAdd);
                        if (passengerToAdd != null) {
                            myCar.addPassenger(user, passengerToAdd);

                            passengerToAdd.setVehicleId(myCar.getId());
                            passengerToAdd.setVehicleModel(myCar.getVehicleModel());
                            passengersDAO.update(passengerToAdd);
                        } else {
                            System.out.println("Passenger not found.");
                        }
                    } else {
                        System.out.println("Please select a passenger and a vehicle first.");
                    }
                    break;
                case 23:
                    if (user != null && myCar != null) {
                        System.out.print("Enter passenger ID to remove: ");
                        int passengerIdToRemove = scan.nextInt();
                        scan.nextLine();
                        Passenger passengerToRemove = passengersDAO.findById(passengerIdToRemove);

                        if (passengerToRemove != null) {
                            myCar.removePassenger(user, passengerToRemove);

                            passengerToRemove.setVehicleId(null);
                            passengerToRemove.setVehicleModel(null);
                            passengersDAO.update(passengerToRemove);
                        } else {
                            System.out.println("Passenger not found.");
                        }
                    } else {
                        System.out.println("Please select a passenger and a vehicle first.");
                    }
                    break;
                case 24:
                    if (user != null && myCar != null) {
                        List<Passenger> passengers = myCar.getPassengers();
                        if (passengers != null && !passengers.isEmpty()) {
                            System.out.println("Passengers in the vehicle:");
                            for (Passenger passenger : passengers) {
                                System.out.println(passenger);
                            }
                        } else {
                            System.out.println("No passengers in the vehicle.");
                        }
                    } else {
                        System.out.println("Please select a passenger and a vehicle first.");
                    }
                    break;
                case 25:
                    if (user != null && myCar != null) {
                        carDAO.delete(myCar.getId());
                        System.out.println("Vehicle deleted successfully.");
                        myCar = null;
                    } else {
                        System.out.println("Please select a passenger and a vehicle first.");
                    }
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option!");
            }

        } while (menu != 0);
    }

}