package com.ticketBackend.ticketingSystem.config;

import com.ticketBackend.ticketingSystem.model.SimulationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public SimulationConfig simulationConfig() {
        return new SimulationConfig(1000, 100, 5000, 3000, 3, 10);
    }
}
