package com.decoticket.api.domain.coupon;

import java.util.Date;

public record CouponRequestDTO(String code, float discount, boolean percentage, Date valid) {
}
