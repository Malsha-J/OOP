package com.ticketBackend.ticketingSystem.repo;

import com.ticketBackend.ticketingSystem.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepo extends MongoRepository<Ticket, String> {
    List<Ticket> findBySoldFalse();
    long countBySoldFalse();
    long countBySoldTrue();
}
