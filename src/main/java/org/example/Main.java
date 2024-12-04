package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Please Enter Total Number of Tickets: ");
        int totalTickets = input.nextInt();

        System.out.println("Please Enter Ticket Release Rate: ");
        int ticketReleaseRate = input.nextInt();

        System.out.println("Please Enter Customer Retrieval Rate: ");
        int customerRetrievalRate = input.nextInt();

        System.out.println("Please Enter Maximum Ticket Capacity: ");
        int maxTicketCapacity = input.nextInt();

    }
}