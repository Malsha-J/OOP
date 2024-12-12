package com.ticketBackend.ticketingSystem.controller;

import com.ticketBackend.ticketingSystem.service.SimulationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chart")
public class ChartController {

    private final SimulationService simulationService;

    public ChartController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @GetMapping
    public List<Map<String, Object>> getChartData() {
        return simulationService.getTicketData();
    }
}
