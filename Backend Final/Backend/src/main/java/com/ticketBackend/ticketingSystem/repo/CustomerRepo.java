package com.ticketBackend.ticketingSystem.repo;

import com.ticketBackend.ticketingSystem.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends MongoRepository<Customer, String> {
}
