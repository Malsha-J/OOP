package org.example;

/**
 * Represents a vendor in the system that generates and adds tickets to the pool.
 * Implements Runnable for concurrent operation.
 */

public class Vendor implements Runnable {

    private String vendorName;
    private TicketPool ticketPool;
    private Configuration config;
    public boolean running = false;

    public Vendor(TicketPool ticketPool,String vendorName, Configuration config) {
        this.config=config;
        this.ticketPool = ticketPool;
        this.vendorName = vendorName;
    }

    @Override

    /**
     * Continuously generates tickets and adds them to the ticket pool
     * at the configured release rate.
     */

    public void run() {


        while(!running) {
            for(int i=0;i<config.getTotalTickets();i++){
                this.ticketPool.addTickets();
                try{
                    Thread.sleep(config.getTicketReleaseRate());
                    //Thread.sleep(config.getTicketReleaseRate());
                } catch (InterruptedException e) {
                    System.out.println("Something went wrong with "+this.vendorName);
                }
                System.out.println(this.vendorName +" added a Ticket");
            }
        }
    }


}
