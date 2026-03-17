# Smart Parking System Backend

This is the backend part of the Smart Parking System project, built using Spring Boot. The backend provides RESTful APIs to manage parking slots and vehicles.

## Features

- **Add Parking Slots**: Allows the addition of new parking slots to the system.
- **Park Vehicles**: Enables parking of vehicles in available slots.
- **Remove Vehicles**: Facilitates the removal of vehicles from the parking slots.

## Project Structure

```
src
└── main
    └── java
        └── com
            └── smartparkingsystem
                ├── SmartParkingSystemApplication.java
                ├── controller
                │   └── ParkingController.java
                ├── model
                │   └── ParkingSlot.java
                ├── service
                │   └── ParkingService.java
                └── repository
                    └── ParkingRepository.java
```

## Getting Started

1. **Clone the repository**:
   ```
   git clone <repository-url>
   ```

2. **Navigate to the backend directory**:
   ```
   cd smart-parking-system/backend
   ```

3. **Build the project**:
   ```
   mvn clean install
   ```

4. **Run the application**:
   ```
   mvn spring-boot:run
   ```

## Configuration

The application properties can be configured in `src/main/resources/application.properties`. Make sure to set up your database connection settings accordingly.

## Dependencies

The project uses Maven for dependency management. You can find the list of dependencies in the `pom.xml` file.

## API Documentation

Refer to the `ParkingController` class for the available endpoints and their usage.