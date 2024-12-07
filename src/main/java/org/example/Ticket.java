package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Ticket {
    private int ticketId;
    private String price;
    private String eventTime;
    private String issueDate;

//    public Ticket(int ticketId, int vendorId, float price, float eventTime) {
//        this.ticketId = ticketId;
//        this.vendorId = vendorId;
//        this.price = price;
//        this.eventTime = eventTime;
//    }
//

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

    public String getPrice() {
        return price;
    }

    public String getEventTime() {
        return eventTime;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }
}
