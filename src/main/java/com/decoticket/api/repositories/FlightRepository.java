package com.decoticket.api.repositories;

import com.decoticket.api.domain.flight.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlightRepository extends JpaRepository<Flight, UUID> {
}
