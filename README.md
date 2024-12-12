# Ticket Simulation System

## Introduction
The Ticket Simulation System is a scalable application designed to simulate a ticketing system with both administrative and customer-facing features. The system includes real-time analytics, dynamic thread management for simulation, and an interactive user interface built with React for the frontend and Spring Boot for the backend. MongoDB is used for data storage, enabling efficient and flexible data handling.

---

## Setup Instructions

### Prerequisites
- **Java Version:** JDK 17 or later  
- **Node.js Version:** 16.x or later  
- **MongoDB:** Installed and running locally or on a server  
- **Maven:** Installed for building the backend application

### How to Build and Run the Application

#### Backend Setup
1. Navigate to the backend directory:
   ```bash
   cd Backend
   ```
2. Build the backend application using Maven:
   ```bash
   mvn clean install
   ```
3. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
   The backend will start on `http://localhost:8080` by default.

#### Frontend Setup
1. Navigate to the frontend directory:
   ```bash
   cd Frontend
   ```
2. Install dependencies using npm:
   ```bash
   npm install
   ```
3. Start the React development server:
   ```bash
   npm run dev
   ```
   The frontend will start on `http://localhost:3000` by default.

#### MongoDB Setup
- Ensure MongoDB is running locally or connect to a remote MongoDB instance.
- Update the MongoDB connection string in the `application.properties` file under the `Backend` directory:
  ```properties
  spring.data.mongodb.uri=mongodb://localhost:27017/ticketing_system
  ```

---

## API Endpoints

### Admin Endpoints
- **Get Simulation Configuration**  
  `GET /admin/config`  
  Fetches the current simulation configuration.

- **Update Simulation Configuration**  
  `POST /admin/config`  
  Updates the simulation configuration.

- **Login as Admin**  
  `POST /admin/login`  
  Allows admin login with credentials.

- **Start Simulation**  
  `POST /admin/simulation/start`  
  Starts the ticket simulation.

- **Stop Simulation**  
  `POST /admin/simulation/stop`  
  Stops the ticket simulation.

### Log Endpoints
- **Get Logs**  
  `GET /api/logs`  
  Fetches logs related to the simulation.

### Statistics Endpoints
- **Get Statistics**  
  `GET /api/stats`  
  Provides statistics such as tickets issued, tickets sold, and active vendors/customers.

---

## Usage Instructions

### Configuring and Starting the System
1. Open the frontend in a web browser by navigating to `http://localhost:3000`.
2. Use the **Simulation Control** section in the UI to configure parameters such as:
   - Total Tickets
   - Maximum Ticket Capacity
   - Ticket Release Rate
   - Customer Retrieval Rate
   - Number of Vendors
   - Number of Customers
3. Click the **Start Simulation** button to begin the ticket simulation process.

### UI Controls Explanation
#### Simulation Control Panel
- **Start/Stop Simulation:** Toggles the simulation state. When started, vendor and customer threads will operate based on the configuration.
- **Total Tickets:** Specifies the total number of tickets to be generated.
- **Max Ticket Capacity:** Determines the maximum number of tickets that can be held at a time.
- **Ticket Release Rate:** Sets the frequency (in milliseconds) at which tickets are released by vendors.
- **Customer Retrieval Rate:** Sets the frequency (in milliseconds) at which customers attempt to purchase tickets.
- **Vendor and Customer Counts:** Adjust the number of vendor and customer threads participating in the simulation.

#### Real-Time Analytics
- Displays statistics such as total tickets, tickets sold, active vendors, and active customers.
- Includes a live-updating line chart showing ticket sales over time.

#### Event Log
- Displays real-time logs of simulation activities, such as ticket purchases and vendor actions.

---

## Notes
- Ensure MongoDB is properly set up before starting the backend to avoid connection issues.
- Logs can be viewed in real-time on the frontend or in the backend console for debugging purposes.
- Both the frontend and backend should be running simultaneously for full functionality.
