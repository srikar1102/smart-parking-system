# Smart Parking System

## Overview
This repository implements a complete Smart Parking System that includes:
- Spring Boot backend service (Java, JPA, SQLite)
- Frontend UI (vanilla JS/HTML/CSS)
- REST API for parking operations
- Slot management and consolidation logic

## Repository Structure
```
new-project/
  README.md
  package.json (frontend template)
  src/
    index.js (template)
    smart-parking-system/
      backend/
        pom.xml
        src/main/java/com/smartparkingsystem/...
        src/main/resources/application.properties
      frontend/
        src/app.js
        src/index.html
        src/styles.css
```

## Requirements
### System
- Java 17+ (Java 22 tested)
- Maven 3.6+
- Node.js 16+ (for local static frontend if needed)

### Key Dependencies
- Spring Boot 2.5.4
- Spring Data JPA
- SQLite JDBC driver
- Vanilla JavaScript + HTML/CSS (frontend)

## Backend Setup and Execution
### 1. Start Backend Service
```bash
cd new-project/src/smart-parking-system/backend
mvn clean package -DskipTests
mvn spring-boot:run
```
If port 8081 is in use, stop the occupying process or run with an alternate port:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8082"
```

### 2. API Endpoints
- `GET /api/parking/slots` : List all slots (auto-consolidates 1..50)
- `POST /api/parking/slots` : Add a slot manually
  - payload: `{ "id": 12, "status": "free" }`
- `POST /api/parking/slots/consolidate` : Ensure slots 1..50 exist
- `POST /api/parking/park` : Park a vehicle
  - payload: `{ "slotId": 3, "vehicleNumber": "ABC123" }`
- `DELETE /api/parking/remove/{slotId}` : Free a slot (vehicle exit)
- `DELETE /api/parking/slots/{slotId}` : Delete a slot entry

### 3. Data Model
- `ParkingSlot` with fields:
  - `Long id`
  - `String status` (`free` / `occupied`)
  - `String vehicleDetails` (number)
  - `String vehicleType` (legacy/optional, currently unused)

## Frontend Setup and Execution
### 1. Open the UI
- open `src/smart-parking-system/frontend/src/index.html` in a browser
- or serve via local static server if needed.

### 2. JavaScript behavior
- `app.js` calls API to refresh slots and populate UI controls
- `Park Vehicle` sends JSON payload to backend
- `Vehicle Exit` and `Delete Slot` operations supported
- `steps`:
  1. choose slot from dropdown or card
  2. enter `Vehicle Number`
  3. click `Park Vehicle`

## Features implemented
- slot status toggling free/occupied
- vehicle parking/exit
- slot deletion + database persistence
- UI status cards with slot display
