package com.decoticket.api.repositories;

import com.decoticket.api.domain.seat.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, UUID> {
}
