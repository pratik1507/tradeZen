package com.tradeZen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradeZen.model.PaymentDetails;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {
	
	PaymentDetails findByUserId(Long userId);

}
