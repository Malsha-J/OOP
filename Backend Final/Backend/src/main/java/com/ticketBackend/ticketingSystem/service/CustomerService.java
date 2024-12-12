package com.ticketBackend.ticketingSystem.service;

import com.ticketBackend.ticketingSystem.model.Customer;
import com.ticketBackend.ticketingSystem.model.SimulationConfig;
import com.ticketBackend.ticketingSystem.repo.CustomerRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class CustomerService {
    private static final Logger logger = LogManager.getLogger(CustomerService.class);
    private final TicketService ticketService;
    private final CustomerRepo customerRepo;
    private volatile boolean running = true;
    private SimulationConfig config;

    // Use ExecutorService for better thread management
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private final CopyOnWriteArrayList<Future<?>> customerTasks = new CopyOnWriteArrayList<>();

    public CustomerService(TicketService ticketService, CustomerRepo customerRepo, SimulationConfig config) {
        this.ticketService = ticketService;
        this.customerRepo = customerRepo;
        this.config = config;
    }

    public void updateConfig(SimulationConfig config) {
        this.config = config; // Apply updated configuration
    }

    public void simulateCustomer(int customerId) {
        if (executorService.isShutdown()) {
            // Reinitialize the ExecutorService if it was shut down
            executorService = Executors.newCachedThreadPool();
            running = true; // Reset running flag to true
            logger.info("Reinitialized ExecutorService and restarted simulation.");
        }

        Future<?> task = executorService.submit(() -> {
            Thread.currentThread().setName("customerThread-" + customerId);
            logger.info("Customer {} thread started.", customerId);

            // Create a customer object and save it in MongoDB
            Customer customer = new Customer();
            customer.setId(String.valueOf(customerId));
            customer.setName("Customer " + customerId);
            customer.setEmail("customer" + customerId + "@example.com");
            customerRepo.save(customer); // Save to MongoDB

            while (running && ticketService.purchaseTicket(customerId)) {
                try {
                    Thread.sleep(config.getCustomerRetrievalRate());
                    customer.getPurchasedTickets().add("Ticket-" + System.currentTimeMillis());
                    customerRepo.save(customer); // Update the customer in MongoDB
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.error("Customer {} thread interrupted.", customerId);
                    break;
                }
            }
            logger.info("Customer {} thread exiting.", customerId);
        });

        customerTasks.add(task);
    }

    public void stopAllCustomers() {
        logger.info("Stopping all customer threads...");
        running = false; // Stop the simulation

        // Cancel all customer tasks
        for (Future<?> task : customerTasks) {
            task.cancel(true); // Interrupt threads managed by ExecutorService
        }
        executorService.shutdownNow(); // Shut down the executor service
        customerTasks.clear(); // Clear the list of tasks
        logger.info("All customer threads stopped.");
    }

    public void startSimulation() {
        if (executorService.isShutdown()) {
            // Reinitialize the ExecutorService if it was shut down
            executorService = Executors.newCachedThreadPool();
            logger.info("ExecutorService reinitialized for the simulation.");
        }
        running = true; // Set running flag to true
    }

    public int getActiveCustomers() {
        return (int) customerTasks.stream().filter(task -> !task.isDone()).count();
    }
}
