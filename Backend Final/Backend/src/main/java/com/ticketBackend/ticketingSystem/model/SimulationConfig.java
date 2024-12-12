package com.ticketBackend.ticketingSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
public class SimulationConfig {
    private int totalTickets;
    private int maxCapacity;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int numVendors;
    private int numCustomers;
}
