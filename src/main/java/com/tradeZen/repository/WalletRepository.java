package com.tradeZen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradeZen.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

	Wallet findByUserId(Long userId);
}
