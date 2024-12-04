package org.example;
import java.util.*;
import java.io.Serializable;

public class Configuration implements Serializable {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    public Configuration() {

        Scanner input = new Scanner(System.in);
        boolean isValid = true;


        do {
            try {
                System.out.println("Please Enter Ticket Release Rate: ");
                ticketReleaseRate = input.nextInt();
                isValid = false;

            }catch (Exception e) {
                System.out.println("Please enter a valid number");
                ticketReleaseRate = input.nextInt();
            }
        }while(isValid);

        do {
            try {
                System.out.println("Please Enter Customer Retrieval Rate: ");
                customerRetrievalRate = input.nextInt();
                isValid = false;
            }catch (Exception e) {
                System.out.println("Please enter a valid number");
                customerRetrievalRate = input.nextInt();
            }
        }while(isValid);
        do {
            try{
                System.out.println("Please Enter Max Ticket Capacity: ");
                maxTicketCapacity = input.nextInt();
                isValid = false;

            }catch (Exception e) {
                System.out.println("Please enter a valid number");
            }
        }while (isValid);


    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                '}';
    }
}
