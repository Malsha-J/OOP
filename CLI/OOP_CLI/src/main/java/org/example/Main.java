package org.example;
import com.google.gson.Gson;

import java.io.*;
import java.util.Scanner;

public class Main {

    private static Configuration config = new Configuration();
    private static TicketPool ticketPool = new TicketPool(config);
    public static volatile boolean running = false;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String run;

        while(true){
            System.out.println("Please enter start to start the process and enter stop to stop the process: ");
            String runCommand = scanner.nextLine();
            run = runCommand.toLowerCase();
            if(run.equals("start")){
                break;
            } else if (run.equals("stop")) {
                System.out.println("Process has not started yet.");

            }
        }

        File file = new File("config.json");

        if(file.exists()){
            try(Reader reader = new FileReader("config.json")) {
                config = new Gson().fromJson(reader, Configuration.class);

                TicketPool ticketPool = new TicketPool(config);
                threadingMethod(ticketPool, config);

            } catch (Exception e) {
                System.out.println("Something is wrong");;
            }
        }else{
            config.validation();
            threadingMethod(ticketPool, config);
            config.saveToJson();
        }

        while(!running){
            String command = scanner.nextLine().toLowerCase();
            if(command.equals("stop")){
                running = true;
                System.out.println("Thank you for using our service.");
                System.exit(0);
            }
        }

    }

    public static void threadingMethod(TicketPool ticketPool, Configuration config) {

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
