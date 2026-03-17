
package com.smartparkingsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ParkingSlot {
    @Id
    private Long id;
    private String status; // occupied or free
    private String vehicleDetails;
    private String vehicleType;

    public ParkingSlot() {
    }

    public ParkingSlot(Long id, String status, String vehicleDetails, String vehicleType) {
        this.id = id;
        this.status = status;
        this.vehicleDetails = vehicleDetails;
        this.vehicleType = vehicleType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(String vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}