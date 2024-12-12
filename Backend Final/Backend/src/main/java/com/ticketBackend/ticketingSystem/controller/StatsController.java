package com.ticketBackend.ticketingSystem.controller;

import com.ticketBackend.ticketingSystem.service.CustomerService;
import com.ticketBackend.ticketingSystem.service.TicketService;
import com.ticketBackend.ticketingSystem.service.VendorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final CustomerService customerService;
    private final TicketService ticketService;
    private final VendorService vendorService;

    public StatsController(CustomerService customerService, TicketService ticketService, VendorService vendorService) {
        this.customerService = customerService;
        this.ticketService = ticketService;
        this.vendorService = vendorService;
    }

    @GetMapping
    public Map<String, Object> getStats() {
        return Map.of(
                "issuedTickets", ticketService.getIssuedTickets(),
                "ticketsSold", ticketService.getTicketsSold(),
                "activeVendors", vendorService.getActiveVendors(),
                "activeCustomers", customerService.getActiveCustomers()
        );
    }
}
