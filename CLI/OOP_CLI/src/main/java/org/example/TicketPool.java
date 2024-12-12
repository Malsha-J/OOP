package org.example;

import java.util.*;

/**
 * Manages the pool of tickets shared between vendors and customers.
 * Implements thread-safe operations for adding and removing tickets.
 */

public class TicketPool {

    private Configuration config;
    private final List<Ticket> ticketList = new ArrayList<>();
    /**
     * Thread-safe list to store tickets
     */
    private final List<Ticket> synchronizedList = Collections.synchronizedList(ticketList);

    public TicketPool(Configuration config) {
        this.config = config;
    }

    public synchronized void addTickets(){

        /**
         * Adds a ticket to the pool in a thread-safe manner.
         * Blocks if the pool is at maximum capacity.
         */

        while (synchronizedList.size() == config.getMaxTicketCapacity()){
            System.out.println(synchronizedList.size());
            System.out.println(config.getMaxTicketCapacity());
            try{
                System.out.println("Please wait....");
                wait();

            }catch(InterruptedException e){
                e.printStackTrace();
                System.out.println("Something went wrong");
            }
        }

        Ticket ticket = new Ticket();
        this.synchronizedList.add(ticket);
        notifyAll();
        //System.out.println("Issued date : "+ticket.getIssueDate());

    }

    public synchronized void removeTickets(){

        /**
         * Removes a ticket from the pool in a thread-safe manner.
         * Blocks if the pool is empty.
         */

        while(synchronizedList.isEmpty() ){
            try {
                System.out.println("Please wait until a Ticket available");
                wait();


            } catch (Exception e) {
                System.out.println("Something went wrong with Ticket Pool");;
            }
        }

        // Remove the last item in a synchronized block
        if (!synchronizedList.isEmpty()) {
            this.synchronizedList.remove(synchronizedList.size() - 1);
            notifyAll();
        }

    }

}
