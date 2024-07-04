package com.application.cab_application.Models;

import com.application.cab_application.enums.VehicleType;

import java.util.HashMap;
import java.util.Map;

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

    public void setId(int id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Map<String, Object> vehicleTableMapper(){
        Map<String, Object> vehicleMapper = new HashMap<>();
        vehicleMapper.put("vehicle_type", vehicleType.getCode());
        vehicleMapper.put("vehicle_number", vehicleNumber);
        vehicleMapper.put("brand", brand);
        vehicleMapper.put("year", year);
        vehicleMapper.put("model", model);
        return vehicleMapper ;
    }
}
