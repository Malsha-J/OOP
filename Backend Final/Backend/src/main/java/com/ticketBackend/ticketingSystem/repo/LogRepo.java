package com.ticketBackend.ticketingSystem.repo;

import com.ticketBackend.ticketingSystem.model.LogEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepo extends MongoRepository<LogEntry, String> {
}
