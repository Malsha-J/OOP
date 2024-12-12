package com.ticketBackend.ticketingSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketResponse {
    private int ticketId;
    private String ticketName;
}
