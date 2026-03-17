package com.smartparkingsystem;

import com.smartparkingsystem.model.ParkingSlot;
import com.smartparkingsystem.repository.ParkingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.LongStream;

@SpringBootApplication
public class SmartParkingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartParkingSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedParkingSlots(ParkingRepository parkingRepository) {
        return args -> {
            if (parkingRepository.count() == 0) {
                LongStream.rangeClosed(1, 50).forEach(id -> {
                    ParkingSlot slot = new ParkingSlot(id, "free", null, null);
                    parkingRepository.save(slot);
                });
                System.out.println("Initialized 50 parking slots (free).");
            }
        };
    }
}