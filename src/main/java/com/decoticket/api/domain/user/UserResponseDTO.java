package com.decoticket.api.domain.user;

import com.decoticket.api.domain.role.Role;
import com.decoticket.api.domain.ticket.Ticket;

import java.util.Set;
import java.util.UUID;

public record UserResponseDTO(UUID id, String email, String fullName, String identification, Set<Role> roles, boolean isActive, Ticket ticket) {
}
