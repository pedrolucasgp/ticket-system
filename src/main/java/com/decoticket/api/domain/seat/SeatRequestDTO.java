package com.decoticket.api.domain.seat;

import com.decoticket.api.domain.flight.Flight;
import com.decoticket.api.domain.user.User;

public record SeatRequestDTO(String seatNumber, boolean occupied, String seatClass, float price, Flight flight, User user) {
}
