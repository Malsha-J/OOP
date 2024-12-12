package com.ticketBackend.ticketingSystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "logs")
public class LogEntry {
    @Id
    private String id;
    private String timestamp;
    private String message;
}
