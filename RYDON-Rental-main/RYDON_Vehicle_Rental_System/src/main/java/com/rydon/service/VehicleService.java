package com.rydon.service;

import java.util.List;

import com.rydon.dao.VehicleDAO;
import com.rydon.model.Vehicle;

public class VehicleService {
    private VehicleDAO vehicleDAO;

    public VehicleService(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleDAO.getAllVehicles();
    }
    
    public List<Vehicle> getAvailableVehicles() {
        return vehicleDAO.getAvailableVehicles();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleDAO.addVehicle(vehicle);
    }
    
    public void updateVehicle(Vehicle vehicle) {
        vehicleDAO.updateVehicle(vehicle);
    }

    public void updateVehicleAvailability(int vehicleId, boolean availability) {
    vehicleDAO.updateVehicleAvailability(vehicleId, availability);
}

    public void deleteVehicle(int vehicleId) {
        vehicleDAO.deleteVehicle(vehicleId);
    }
}