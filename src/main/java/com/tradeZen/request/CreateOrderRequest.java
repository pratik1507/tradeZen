package com.tradeZen.request;

import com.tradeZen.domain.OrderType;

import lombok.Data;

@Data
public class CreateOrderRequest {

	private String coinId;
	private double quanity;
	private OrderType orderType;
}
