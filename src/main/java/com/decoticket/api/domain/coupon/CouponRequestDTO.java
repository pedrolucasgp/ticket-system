package com.decoticket.api.domain.coupon;

public record CouponRequestDTO(String code, float discount, boolean percentage, boolean isValid) {
}
