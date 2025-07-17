package com.decoticket.api.domain.flight;

import com.decoticket.api.domain.seat.SeatResponseDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record FlightResponseDTO(UUID id, String departure, String destination, LocalDateTime departureDate, LocalDateTime arrivalDate, List<SeatResponseDTO> seats) {
}
