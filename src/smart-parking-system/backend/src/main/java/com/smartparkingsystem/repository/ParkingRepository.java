package com.smartparkingsystem.repository;

import com.smartparkingsystem.model.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends JpaRepository<ParkingSlot, Long> {
}