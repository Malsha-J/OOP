package com.ticketBackend.ticketingSystem.controller;

import com.ticketBackend.ticketingSystem.model.LogEntry;
import com.ticketBackend.ticketingSystem.repo.LogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LogController {

    private static final String LOG_FILE_PATH = "logs/application.log";

    @Autowired
    private LogRepo logRepo;

    @GetMapping("/api/logs")
    public List<LogEntry> getLogs() {
        List<LogEntry> logs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(LOG_FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.contains("c.t.t.service.CustomerService") ||
                        line.contains("c.t.t.service.VendorService") ||
                        line.contains("c.t.t.service.TicketService")) {

                    LogEntry log = parseLog(line);
                    if (log != null) {
                        logs.add(log);
                        logRepo.save(log); // Save the log entry to MongoDB
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return logs;
    }

    private LogEntry parseLog(String line) {
        String[] parts = line.split(" : ");
        if (parts.length == 2) {
            String timestamp = parts[0];
            String message = parts[1];
            return new LogEntry(null, timestamp, message);
        }
        return null;
    }

//    // Log model class
//    @Data
//    public static class Log {
//        private String timestamp;
//        private String message;
//
//        public Log(String timestamp, String message) {
//            this.timestamp = timestamp;
//            this.message = message;
//        }
//    }
}

