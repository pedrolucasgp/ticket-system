package com.decoticket.api.domain.coupon;

import java.util.UUID;

public record CouponResponseDTO(UUID id, String code, float discount, boolean percentage, boolean isValid) {
}
