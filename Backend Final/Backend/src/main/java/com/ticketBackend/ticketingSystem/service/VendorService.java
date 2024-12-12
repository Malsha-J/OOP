package com.ticketBackend.ticketingSystem.service;

import com.ticketBackend.ticketingSystem.model.SimulationConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class VendorService {
    private static final Logger logger = LogManager.getLogger(VendorService.class);
    private final TicketService ticketService;
    private volatile boolean running = true;
    private SimulationConfig config;

    // Use ExecutorService for better thread management
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private final CopyOnWriteArrayList<Future<?>> vendorTasks = new CopyOnWriteArrayList<>();

    public VendorService(TicketService ticketService, SimulationConfig config) {
        this.ticketService = ticketService;
        this.config = config;
    }

    public void updateConfig(SimulationConfig config) {
        this.config = config; // Apply updated configuration
    }

    public void simulateVendor(int vendorId) {
        if (executorService.isShutdown()) {
            // Reinitialize the ExecutorService if it was shut down
            executorService = Executors.newCachedThreadPool();
            running = true; // Reset running flag to true
            logger.info("Reinitialized ExecutorService and restarted simulation.");
        }

        Future<?> task = executorService.submit(() -> {
            Thread.currentThread().setName("vendorThread-" + vendorId);
            logger.info("Vendor {} thread started.", vendorId);

            while (running && !ticketService.isAllTicketsProduced()) {
                int ticketsPerBatch = config.getMaxCapacity() / config.getNumVendors();
                ticketService.addTickets(ticketsPerBatch);
                logger.info("Vendor {} added {} tickets.", vendorId, ticketsPerBatch);
                try {
                    Thread.sleep(config.getTicketReleaseRate());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.error("Vendor {} thread interrupted.", vendorId);
                    break;
                }
            }
            logger.info("Vendor {} thread exiting.", vendorId);
        });

        vendorTasks.add(task);
    }

    public void stopAllVendors() {
        logger.info("Stopping all vendor threads...");
        running = false; // Stop the simulation

        // Cancel all vendor tasks
        for (Future<?> task : vendorTasks) {
            task.cancel(true); // Interrupt threads managed by ExecutorService
        }
        executorService.shutdownNow(); // Shut down the executor service
        vendorTasks.clear(); // Clear the list of tasks
        logger.info("All vendor threads stopped.");
    }

    public void startSimulation() {
        if (executorService.isShutdown()) {
            // Reinitialize the ExecutorService if it was shut down
            executorService = Executors.newCachedThreadPool();
            logger.info("ExecutorService reinitialized for the simulation.");
        }
        running = true; // Set running flag to true
    }

    public int getActiveVendors() {
        return (int) vendorTasks.stream().filter(task -> !task.isDone()).count();
    }
}
