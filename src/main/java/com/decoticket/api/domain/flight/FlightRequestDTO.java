package com.decoticket.api.domain.flight;

import com.decoticket.api.domain.seat.Seat;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record FlightRequestDTO(String departure, String destination, LocalDateTime departureDate, LocalDateTime arrivalDate, int seatCount) {
}
