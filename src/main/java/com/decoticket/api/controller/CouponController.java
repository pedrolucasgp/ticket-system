package com.decoticket.api.controller;

import com.decoticket.api.domain.coupon.Coupon;
import com.decoticket.api.domain.coupon.CouponRequestDTO;
import com.decoticket.api.domain.coupon.CouponResponseDTO;
import com.decoticket.api.services.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping("")
    public ResponseEntity<List<CouponResponseDTO>> getCoupons(){
        List<CouponResponseDTO> allCoupons = this.couponService.listAll();
        return ResponseEntity.ok(allCoupons);
    }

    @PostMapping("")
    public ResponseEntity<Coupon> create(@RequestBody CouponRequestDTO body){
        Coupon newCoupon = this.couponService.createCoupon(body);
        return ResponseEntity.ok(new Coupon());
    }

    @PutMapping("")
    public ResponseEntity<Coupon> edit(@RequestBody CouponResponseDTO body){
        Coupon editCoupon = couponService.editCoupon(body);
        return ResponseEntity.ok(editCoupon);
    }
}
