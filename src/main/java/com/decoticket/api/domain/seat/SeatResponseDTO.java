package com.decoticket.api.domain.seat;

import com.decoticket.api.domain.user.User;

import java.util.UUID;

public record SeatResponseDTO(String seatNumber, String seatClass, boolean occupied, float price, User user) {
}
