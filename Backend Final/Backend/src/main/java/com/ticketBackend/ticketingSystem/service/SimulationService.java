package com.ticketBackend.ticketingSystem.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SimulationService {

    private final List<Map<String, Object>> ticketData = new ArrayList<>();
    private int issuedTickets = 0;
    private int purchasedTickets = 0;

    public SimulationService() {
        // Initialize with some data
        ticketData.add(Map.of("time", LocalTime.now().toString(), "issued", issuedTickets, "purchased", purchasedTickets));
    }

    @Scheduled(fixedRate = 1000) // Update every 1s
    public void simulateData() {
        // Increment the tickets randomly
        issuedTickets += (int) (Math.random() * 10); // Randomly issue 0-10 tickets
        purchasedTickets += (int) (Math.random() * 5); // Randomly purchase 0-5 tickets

        // Add the new data point
        ticketData.add(Map.of(
                "time", LocalTime.now().toString(),
                "issued", issuedTickets,
                "purchased", purchasedTickets
        ));

        // Limit data to the last 20 points to avoid memory issues
        if (ticketData.size() > 20) {
            ticketData.remove(0);
        }
    }

    public List<Map<String, Object>> getTicketData() {
        return ticketData;
    }
}
