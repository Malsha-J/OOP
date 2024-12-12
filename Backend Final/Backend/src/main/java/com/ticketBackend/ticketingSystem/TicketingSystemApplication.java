package com.ticketBackend.ticketingSystem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class TicketingSystemApplication {
	private static final Logger logger = LogManager.getLogger(TicketingSystemApplication.class);

	public static void main(String[] args) {
		logger.info("Starting Ticketing System Application...");
		ConfigurableApplicationContext context = SpringApplication.run(TicketingSystemApplication.class, args);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			logger.info("Shutting down Ticketing System Application...");
			context.close();
		}));
	}

}
