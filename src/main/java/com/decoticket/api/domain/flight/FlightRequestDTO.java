package com.decoticket.api.domain.flight;

import com.decoticket.api.domain.seat.Seat;

import java.time.LocalDateTime;

public record FlightRequestDTO(String departure, String destination, LocalDateTime departureDate, LocalDateTime arrivalDate, Seat seats) {
}
