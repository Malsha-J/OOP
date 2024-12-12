package com.ticketBackend.ticketingSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "tickets")
public class Ticket {
    @Id
    private String id;
    private String ticketId;
    private boolean sold;
    private String customerId;
    private String customerName;
    private String customerEmail;
    private String contactNo;
    private String type;
}
