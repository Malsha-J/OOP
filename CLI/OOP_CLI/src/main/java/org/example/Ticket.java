package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * Represents an individual ticket in the system.
 * Contains ticket details such as price, event time, and issue date.
 */

public class Ticket {

    /**
     * Gets the current date formatted as yyyy-MM-dd
     *
     * @return String representation of current date
     */

    private int ticketId;
    private String price;
    private String eventTime;
    private String issueDate;

    public Ticket() {
        this.issueDate = getCurrentDate();
        this.price = "Rs 20000";
        this.eventTime = "10.00 pm";
    }

    public String getCurrentDate(){
        LocalDate now = LocalDate.now(); // Get the current date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Format to "yyyy-MM-dd"
        return now.format(formatter);
    }

}
