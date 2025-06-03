package com.decoticket.api.domain.user;

import com.decoticket.api.domain.ticket.Ticket;

import java.util.UUID;

public record UserResponseDTO(UUID id, String email, String fullName, String identification, String role, boolean isActive, Ticket ticket) {
}
