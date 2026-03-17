package com.smartparkingsystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartparkingsystem.model.ParkingSlot;
import com.smartparkingsystem.service.ParkingService;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @PostMapping("/slots")
    public ResponseEntity<ParkingSlot> addParkingSlot(@RequestBody ParkingSlot parkingSlot) {
        ParkingSlot createdSlot = parkingService.addParkingSlot(parkingSlot);
        return ResponseEntity.ok(createdSlot);
    }


    public static class ParkRequest {
        public Long slotId;
        public String vehicleNumber;
    }

    @PostMapping("/park")
    public ResponseEntity<String> parkVehicle(@RequestBody ParkRequest request) {
        if (request == null || request.slotId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Slot ID is required.");
        }
        if (request.vehicleNumber == null || request.vehicleNumber.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Vehicle number is required.");
        }

        Optional<ParkingSlot> slot = parkingService.getParkingSlot(request.slotId);
        if (slot.isPresent() && !"free".equalsIgnoreCase(slot.get().getStatus())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Slot is already occupied.");
        }

        Optional<ParkingSlot> result = parkingService.parkVehicle(request.slotId, request.vehicleNumber);
        if (result.isPresent()) {
            return ResponseEntity.ok("Vehicle parked successfully.");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to park vehicle.");
    }


    @DeleteMapping("/remove/{slotId}")
    public ResponseEntity<ParkingSlot> removeVehicle(@PathVariable Long slotId) {
        return parkingService.removeVehicle(slotId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/slots/{slotId}")
    public ResponseEntity<Void> deleteSlot(@PathVariable Long slotId) {
        if (parkingService.removeSlot(slotId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/slots")
    public ResponseEntity<List<ParkingSlot>> getAllSlots() {
        List<ParkingSlot> slots = parkingService.getAllParkingSlots();
        return ResponseEntity.ok(slots);
    }

    @PostMapping("/slots/consolidate")
    public ResponseEntity<List<ParkingSlot>> consolidateSlots() {
        parkingService.ensureSlotsExist(50);
        return ResponseEntity.ok(parkingService.getAllParkingSlots());
    }
}