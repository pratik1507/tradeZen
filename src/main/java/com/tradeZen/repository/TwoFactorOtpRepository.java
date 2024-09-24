package com.tradeZen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tradeZen.model.TwoFactorOTP;

@Repository
public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP, String> {
	TwoFactorOTP findByUserId(Long userId);

}
