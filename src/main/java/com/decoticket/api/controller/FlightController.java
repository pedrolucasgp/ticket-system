package com.decoticket.api.controller;

import com.decoticket.api.domain.flight.Flight;
import com.decoticket.api.domain.flight.FlightRequestDTO;
import com.decoticket.api.domain.flight.FlightResponseDTO;
import com.decoticket.api.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/flight")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("")
    public ResponseEntity<List<FlightResponseDTO>> getFlights() {
        List<FlightResponseDTO> allFlights = this.flightService.listAll();
        return ResponseEntity.ok(allFlights);
    }

    @PostMapping("")
    public ResponseEntity<Flight> createFlight(FlightRequestDTO body) {
        Flight newFlight = this.flightService.createFlight(body);
        return ResponseEntity.ok(newFlight);
    }

}
