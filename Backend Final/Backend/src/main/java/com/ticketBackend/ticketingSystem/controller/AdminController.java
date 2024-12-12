package com.ticketBackend.ticketingSystem.controller;

import com.ticketBackend.ticketingSystem.model.SimulationConfig;
import com.ticketBackend.ticketingSystem.service.CustomerService;
import com.ticketBackend.ticketingSystem.service.TicketService;
import com.ticketBackend.ticketingSystem.service.VendorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LogManager.getLogger(AdminController.class);

    @Autowired
    private VendorService vendorService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TicketService ticketService;
    private final SimulationConfig simulationConfig;

    public AdminController(VendorService vendorService, CustomerService customerService, TicketService ticketService, SimulationConfig simulationConfig) {
        this.vendorService = vendorService;
        this.customerService = customerService;
        this.ticketService = ticketService;
        this.simulationConfig = simulationConfig;
    }

    @GetMapping("/config")
    public SimulationConfig getConfig() {
        logger.info("Fetching simulation configuration...");
        return simulationConfig;
    }

    @PostMapping("/config")
    public String updateConfig(@RequestBody SimulationConfig config) {
        logger.info("Updating simulation configuration...");
        simulationConfig.setTotalTickets(config.getTotalTickets());
        simulationConfig.setMaxCapacity(config.getMaxCapacity());
        simulationConfig.setTicketReleaseRate(config.getTicketReleaseRate());
        simulationConfig.setCustomerRetrievalRate(config.getCustomerRetrievalRate());
        simulationConfig.setNumVendors(config.getNumVendors());
        simulationConfig.setNumCustomers(config.getNumCustomers());
        logger.info("Simulation configuration updated to: {}", simulationConfig);

        vendorService.updateConfig(simulationConfig);
        customerService.updateConfig(simulationConfig);
        ticketService.updateConfig(simulationConfig);

        return "Configuration updated!";
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if ("admin".equals(username) && "password123".equals(password)) {
            return "Login successful!";
        } else {
            return "Invalid credentials";
        }
    }

    @PostMapping("/simulation/start")
    public void startSimulation() {
        // Use the configuration for vendors and customers
        int vendors = simulationConfig.getNumVendors();
        int customers = simulationConfig.getNumCustomers();
        logger.info("Starting simulation with {} vendors and {} customers.", vendors, customers);
        for (int i = 1; i <= vendors; i++) {
            vendorService.simulateVendor(i);
        }
        for (int i = 1; i <= customers; i++) {
            customerService.simulateCustomer(i);
        }
    }

    @PostMapping("/simulation/stop")
    public void stopSimulation() {
        logger.info("Stopping simulation...");
        vendorService.stopAllVendors();
        customerService.stopAllCustomers();
        logger.info("Simulation stopped.");
    }
}
