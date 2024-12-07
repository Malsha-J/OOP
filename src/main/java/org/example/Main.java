package org.example;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Configuration config = new Configuration();
        TicketPool ticketPool = new TicketPool(config);
        config.validation();

        Vendor vendor1 = new Vendor(ticketPool,"Vendor 1",config);
        Vendor vendor2 = new Vendor(ticketPool,"Vendor 2",config);

        Customer customer1 = new Customer(ticketPool,"Customer 1",config);
        Customer customer2 = new Customer(ticketPool,"Customer 2",config);

        Thread thread1 = new Thread(vendor1);
        Thread thread2 = new Thread(vendor2);
        Thread thread3 = new Thread(customer1);
        Thread thread4 = new Thread(customer2);


        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

    }
}