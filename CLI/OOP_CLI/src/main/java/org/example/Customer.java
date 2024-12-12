package org.example;

/**
 * Represents a customer that retrieves tickets from the pool.
 * Implements Runnable for concurrent operation.
 */

public class Customer implements Runnable {

    /**
     * Continuously attempts to retrieve tickets from the pool
     * at the configured retrieval rate.
     */

    private String CustomerName;
    private TicketPool ticketPool;
    private Configuration config;
    public boolean running = false;

    public Customer(TicketPool ticketPool, String CustomerName,Configuration config) {
        this.config = config;
        this.CustomerName = CustomerName;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {

        while(!running) {
            for (int i=0;i<config.getTotalTickets();i++){
                this.ticketPool.removeTickets();
                try{
                    Thread.sleep(config.getCustomerRetrievalRate());
                } catch (Exception e) {
                    System.out.println("Something went wrong with "+CustomerName+": "+e);;
                }
                System.out.println(this.CustomerName+" Buy a ticket");
            }
        }

    }

}
