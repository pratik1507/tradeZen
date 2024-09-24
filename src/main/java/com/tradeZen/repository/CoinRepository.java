package com.tradeZen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradeZen.model.Coin;

public interface CoinRepository extends JpaRepository<Coin, String> {

}
