package com.smartparkingsystem.service;

import com.smartparkingsystem.model.ParkingSlot;
import com.smartparkingsystem.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingService {

    @Autowired
    private ParkingRepository parkingRepository;

    public ParkingSlot addParkingSlot(ParkingSlot parkingSlot) {
        return parkingRepository.save(parkingSlot);
    }

    public Optional<ParkingSlot> parkVehicle(Long id, String vehicleNumber) {
        if (vehicleNumber == null || vehicleNumber.trim().isEmpty()) {
            return Optional.empty();
        }

        ParkingSlot slot = parkingRepository.findById(id).orElseGet(() -> {
            ParkingSlot newSlot = new ParkingSlot(id, "free", null, null);
            return parkingRepository.save(newSlot);
        });

        if (!"free".equalsIgnoreCase(slot.getStatus())) {
            return Optional.empty();
        }

        slot.setStatus("occupied");
        slot.setVehicleDetails(vehicleNumber.trim());
        slot.setVehicleType(null);
        return Optional.of(parkingRepository.save(slot));
    }

    public Optional<ParkingSlot> removeVehicle(Long id) {
        Optional<ParkingSlot> slot = parkingRepository.findById(id);
        if (slot.isPresent() && slot.get().getStatus().equals("occupied")) {
            ParkingSlot updated = slot.get();
            updated.setStatus("free");
            updated.setVehicleDetails(null);
            updated.setVehicleType(null);
            return Optional.of(parkingRepository.save(updated));
        }
        return Optional.empty();
    }

    public boolean removeSlot(Long id) {
        if (parkingRepository.existsById(id)) {
            parkingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<ParkingSlot> getParkingSlot(Long id) {
        return parkingRepository.findById(id);
    }

    public void ensureSlotsExist(int maxSlots) {
        List<ParkingSlot> existing = parkingRepository.findAll();
        for (long id = 1L; id <= maxSlots; id++) {
            final long key = id;
            boolean exists = existing.stream().anyMatch(slot -> slot.getId().equals(key));
            if (!exists) {
                ParkingSlot slot = new ParkingSlot(id, "free", null, null);
                parkingRepository.save(slot);
            }
        }
    }

    public List<ParkingSlot> getAllParkingSlots() {
        ensureSlotsExist(50);
        return parkingRepository.findAll();
    }
}