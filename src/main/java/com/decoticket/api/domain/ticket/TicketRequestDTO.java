package com.decoticket.api.domain.ticket;

import com.decoticket.api.domain.coupon.Coupon;
import com.decoticket.api.domain.flight.Flight;
import com.decoticket.api.domain.seat.Seat;
import com.decoticket.api.domain.user.User;

import java.time.LocalDateTime;

public record TicketRequestDTO(User user, Seat seat, Flight flight, Coupon coupon, LocalDateTime purchaseDate, boolean isChecked, float finalPrice) {
}
