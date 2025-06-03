package com.decoticket.api.domain.user;

public record UserRequestDTO(String email, String fullName, String password, String identification, boolean isActive) {
}
