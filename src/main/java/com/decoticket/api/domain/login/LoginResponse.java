package com.decoticket.api.domain.login;

public record LoginResponse(String email, String accessToken, Long expiresIn) {
}
