# Smart Parking System

This project is a Smart Parking System that allows users to manage parking slots efficiently. It consists of a backend built with Java Spring Boot and a frontend developed using HTML, CSS, and JavaScript.

## Project Structure

```
smart-parking-system
├── backend
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com
│   │   │   │       └── smartparkingsystem
│   │   │   │           ├── SmartParkingSystemApplication.java
│   │   │   │           ├── controller
│   │   │   │           │   └── ParkingController.java
│   │   │   │           ├── model
│   │   │   │           │   └── ParkingSlot.java
│   │   │   │           ├── service
│   │   │   │           │   └── ParkingService.java
│   │   │   │           └── repository
│   │   │   │               └── ParkingRepository.java
│   │   │   └── resources
│   │   │       └── application.properties
│   ├── pom.xml
│   └── README.md
├── frontend
│   ├── src
│   │   ├── index.html
│   │   ├── styles.css
│   │   └── app.js
│   └── README.md
└── README.md
```

## Features

- **Add Parking Slots**: Users can add new parking slots to the system.
- **Park Vehicles**: Users can park vehicles in available slots.
- **Remove Vehicles**: Users can remove parked vehicles from the slots.

## Technologies Used

- **Backend**: Java, Spring Boot, JPA, Hibernate
- **Frontend**: HTML, CSS, JavaScript

## Getting Started

To run the project, follow these steps:

1. Clone the repository.
2. Navigate to the `backend` directory and run the Spring Boot application.
3. Navigate to the `frontend` directory and open `index.html` in a web browser.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any enhancements or bug fixes.