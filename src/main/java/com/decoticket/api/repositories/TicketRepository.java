package com.decoticket.api.repositories;

import com.decoticket.api.domain.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
}
