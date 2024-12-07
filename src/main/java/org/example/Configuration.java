package org.example;
import java.util.*;
import java.io.Serializable;

public class Configuration implements Serializable {

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

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
                "totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                '}';
    }

    public void validation(){
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

}
