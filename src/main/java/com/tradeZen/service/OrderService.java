package com.tradeZen.service;

import java.util.List;

import com.tradeZen.domain.OrderType;
import com.tradeZen.model.Coin;
import com.tradeZen.model.Order;
import com.tradeZen.model.OrderItem;
import com.tradeZen.model.User;

public interface OrderService {
	
	Order createOrder(User user, OrderItem orderItem, OrderType orderType);
	Order getOrderById(Long orderId) throws Exception;
	List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol);
    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;
    
}
