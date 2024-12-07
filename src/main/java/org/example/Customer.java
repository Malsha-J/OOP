package org.example;

public class Customer implements Runnable {
    private String CustomerName;
    private TicketPool ticketPool;
    private Configuration config;

    public Customer(TicketPool ticketPool, String CustomerName,Configuration config) {
        this.config = config;
        this.CustomerName = CustomerName;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {

        for (int i=0;i<config.getTotalTickets();i++){
            this.ticketPool.removeTickets();
            try{
                Thread.sleep(config.getCustomerRetrievalRate());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(this.CustomerName+" Buy a ticket");
        }


    }
}
