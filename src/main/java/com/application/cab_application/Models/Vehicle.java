package com.application.cab_application.Models;

import com.application.cab_application.enums.VehicleType;

public class Vehicle {
    private int id;
    private String model;
    private VehicleType vehicleType;
    private String vehicleNumber;
    private String brand;
    private int year;

    public Vehicle(int id, String model, VehicleType vehicleType, String vehicleNumber, String brand, int year) {
        this.id = id;
        this.model = model;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.brand = brand;
        this.year = year;
    }

    public Vehicle() {

    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getBrand() {
        return brand;
    }

    public int getYear() {
        return year;
    }
}
