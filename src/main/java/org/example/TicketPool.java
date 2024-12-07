package org.example;

import java.util.*;

public class TicketPool {

    private Configuration config;
    private List<Ticket> ticketList = new ArrayList<>();
    private List<Ticket> synchronizedList = Collections.synchronizedList(ticketList);

    public TicketPool(Configuration config) {
        this.config = config;
    }

    public synchronized void addTickets(){

        while (synchronizedList.size() == config.getMaxTicketCapacity()){
            try{
                wait();
                System.out.println("Please wait....");
            }catch(InterruptedException e){
                e.printStackTrace();
                System.out.println("Something went wrong");
            }
        }

        Ticket ticket = new Ticket();
        this.synchronizedList.add(ticket);

    }

    public synchronized void removeTickets(){

        while(synchronizedList.isEmpty() ){
            try {
                System.out.println("Please wait until a Ticket available");
                wait();

            } catch (Exception e) {
                System.out.println("Something went wrong with Ticket Pool");;
            }
        }

        // Remove the last item in a synchronized block
        synchronized (synchronizedList) {
            if (!synchronizedList.isEmpty()) {
                this.synchronizedList.remove(synchronizedList.size() - 1);
            }
        }

    }

}
