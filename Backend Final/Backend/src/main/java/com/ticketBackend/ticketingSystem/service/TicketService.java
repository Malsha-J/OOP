package com.ticketBackend.ticketingSystem.service;

import com.ticketBackend.ticketingSystem.model.SimulationConfig;
import com.ticketBackend.ticketingSystem.model.Ticket;
import com.ticketBackend.ticketingSystem.repo.TicketRepo;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TicketService {
    private static final Logger logger = LogManager.getLogger(TicketService.class);

    @Autowired
    private TicketRepo ticketRepo;
    @Getter
    private SimulationConfig config;
    private final AtomicInteger ticketsProduced = new AtomicInteger(0);

    public TicketService(TicketRepo ticketRepo, SimulationConfig config) {
        this.ticketRepo = ticketRepo;
        this.config = config;
    }

    public void updateConfig(SimulationConfig config) {
        this.config = config; // Apply updated configuration
    }

    public synchronized void addTickets(int count) {
        logger.info("Adding {} tickets to the pool.", count);
        int maxCapacity = config.getMaxCapacity();
        int totalTickets = config.getTotalTickets();

        int availableCapacity = maxCapacity - (int) ticketRepo.countBySoldFalse();
        int ticketsToAdd = Math.min(count, Math.min(availableCapacity, totalTickets - ticketsProduced.get()));

        for (int i = 0; i < ticketsToAdd; i++) {
            Ticket ticket = new Ticket();
            ticket.setTicketId("Ticket-" + ticketsProduced.incrementAndGet());
            ticket.setSold(false);
            ticket.setType("General");
            ticketRepo.save(ticket);
            logger.info("Added ticket with ID: {}", ticket.getTicketId());
        }
        logger.info("Added {} tickets. Total tickets produced so far: {}", ticketsToAdd, ticketsProduced.get());
    }

    public synchronized boolean purchaseTicket(int customerId) {
        var availableTickets = ticketRepo.findBySoldFalse();
        if (availableTickets.isEmpty()) {
            logger.warn("No tickets available for purchase by customer {}", customerId);
            return false;
        }
        Ticket ticket = availableTickets.get(0);
        ticket.setSold(true);
        ticket.setCustomerId(String.valueOf(customerId));
        ticket.setCustomerName("Simulated " + customerId);
        ticket.setCustomerEmail("demo@gmail.com");
        ticket.setContactNo("0");
        ticketRepo.save(ticket);
        logger.info("Customer {} successfully purchased {}", customerId, ticket.getTicketId());
        return true;
    }

    public boolean isAllTicketsProduced() {
        int totalTickets = config.getTotalTickets();
        return ticketsProduced.get() >= totalTickets;
    }

    public Map<String, Integer> getAvailableTickets() {
        Map<String, Integer> availability = new HashMap<>();
        availability.put("general", (int) ticketRepo.countBySoldFalse());
        availability.put("vip", (int) ticketRepo.countBySoldFalse());
        return availability;
    }

    public int getIssuedTickets() {
        return ticketsProduced.get();
    }

    public int getTicketsSold() {
        return (int) ticketRepo.countBySoldTrue();
    }

    public boolean purchaseTicket(String customerName, String customerEmail, String contactNo, int ticketsCount, String ticketType) {
        return true;
    }
}
