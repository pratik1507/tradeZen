package com.tradeZen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradeZen.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

	
}
