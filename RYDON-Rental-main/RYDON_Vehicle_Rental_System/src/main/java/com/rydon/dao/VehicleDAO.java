package com.rydon.dao;

import java.util.List;

import com.rydon.model.Vehicle;

public interface VehicleDAO {
    void addVehicle(Vehicle vehicle);
    void updateVehicle(Vehicle vehicle);
    void deleteVehicle(int vehicleId);
    Vehicle getVehicleById(int vehicleId);
    List<Vehicle> getAllVehicles();
    List<Vehicle> getAvailableVehicles();
    void updateVehicleAvailability(int vehicleId, boolean availability);
}