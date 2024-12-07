package org.example;

public class Vendor implements Runnable{

    private String vendorName;
    private TicketPool ticketPool;
    private Configuration config;

    public Vendor(TicketPool ticketPool,String vendorName, Configuration config) {
        this.config=config;
        this.ticketPool = ticketPool;
        this.vendorName = vendorName;
    }

    @Override
    public void run() {
        for(int i=0;i<config.getTotalTickets();i++){
            this.ticketPool.addTickets();
            try{
                Thread.sleep(config.getTicketReleaseRate());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(this.vendorName +" added a Ticket");
        }
    }

}
