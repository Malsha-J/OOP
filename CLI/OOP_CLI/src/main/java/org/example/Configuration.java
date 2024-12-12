package org.example;
import java.io.*;
import java.util.*;

import com.google.gson.Gson;

public class Configuration implements Serializable {

    /**
     * Total number of tickets to be processed in the system
     */
    private int totalTickets;
    /**
     * Rate at which tickets are released by vendors (in milliseconds)
     */
    private int ticketReleaseRate;
    /**
     * Rate at which customers can retrieve tickets (in milliseconds)
     */
    private int customerRetrievalRate;
    /**
     * Maximum capacity of tickets that can be held in the pool
     */
    private int maxTicketCapacity;

    public Configuration (){}

    public int getTotalTickets() {
        return this.totalTickets;
    }

    public int getTicketReleaseRate() {
        return this.ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return this.customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return this.maxTicketCapacity;
    }

    public void setTotalTickets(int totalTickets) {this.totalTickets = totalTickets;}

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {this.customerRetrievalRate = customerRetrievalRate;}

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "totalTickets = " + totalTickets +
                ", ticketReleaseRate = " + ticketReleaseRate +
                ", customerRetrievalRate = " + customerRetrievalRate +
                ", maxTicketCapacity = " + maxTicketCapacity +
                '}';
    }

    public void validation(){

        /**
         * Validates user input for configuration parameters.
         * Ensures all parameters are within acceptable ranges.
         */

        Scanner input = new Scanner(System.in);
        // get the maximum number of tickets from user and validate the input
        while(true){
            try{
                System.out.println("Please Enter Maximum Number of Tickets (1 - 10 000 amount only): ");
                maxTicketCapacity = input.nextInt();
                if(1<=maxTicketCapacity && maxTicketCapacity<=10000){
                    setMaxTicketCapacity(maxTicketCapacity);
                    break;
                }else{
                    System.out.println("Please enter only between 1 - 10 000");
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid number");
                input.next();
            }
        }
        // get the total number of tickets from user and validate the input
        while(true){
            try{
                System.out.println("Please Enter Total Tickets (0 < Total Tickets <= "+ maxTicketCapacity + "): ");
                totalTickets = input.nextInt();
                if(totalTickets>0){
                    setTotalTickets(totalTickets);
                    break;
                }else {
                    System.out.println("Please enter only between 0 - "+ maxTicketCapacity);
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid number");
                input.next();
            }
        }
        // get the ticket release rate from user and
        while(true){
            try{
                System.out.println("Please Enter Ticket release rate(ms): ");
                ticketReleaseRate = input.nextInt();
                setTicketReleaseRate(ticketReleaseRate);
                break;
            } catch (Exception e) {
                System.out.println("Please enter a valid number");
                input.next();
            }
        }

        while(true){
            try{
                System.out.println("Please Enter Customer retrieval rate(ms): ");
                customerRetrievalRate = input.nextInt();
                setCustomerRetrievalRate(customerRetrievalRate);
                break;
            } catch (Exception e) {
                System.out.println("Please enter a valid number");
                input.next();
            }
        }

    }

    public void saveToJson(){

        /**
         * Saves the current configuration to a JSON file.
         * Uses Gson for JSON serialization.
         */

        Gson gson = new Gson();
        //String configObj = gson.toJson(config);

        try {
            Writer writer = new FileWriter("config.json");
            gson.toJson(this, writer);
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
