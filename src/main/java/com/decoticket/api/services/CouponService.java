package com.decoticket.api.services;

import com.decoticket.api.domain.coupon.Coupon;
import com.decoticket.api.domain.coupon.CouponRequestDTO;
import com.decoticket.api.domain.coupon.CouponResponseDTO;
import com.decoticket.api.repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CouponService {
    @Autowired
    private CouponRepository repository;

    public List<CouponResponseDTO> listAll(){
        return repository.findAll()
                .stream()
                .map(coupon -> new CouponResponseDTO(
                        coupon.getId(),
                        coupon.getCode(),
                        coupon.getDiscount(),
                        coupon.isPercentage(),
                        coupon.isValid()
                ))
                .toList();
    }

    public Coupon createCoupon(CouponRequestDTO data){
        Coupon newCoupon = new Coupon();
        newCoupon.setCode(data.code());
        newCoupon.setDiscount(data.discount());
        newCoupon.setPercentage(data.percentage());
        newCoupon.setValid((data.isValid()));

        repository.save(newCoupon);

        return newCoupon;
    }

    public Coupon editCoupon( CouponResponseDTO data){
        Coupon existingCoupon = repository.findById(data.id())
                .orElseThrow(() -> new RuntimeException("Coupon not found."));

        existingCoupon.setCode(data.code());
        existingCoupon.setDiscount(data.discount());
        existingCoupon.setPercentage(data.percentage());
        existingCoupon.setValid(data.isValid());

        return repository.save(existingCoupon);
    }
}
