package com.tradeZen.service;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.tradeZen.domain.PaymentMethod;
import com.tradeZen.model.PaymentOrder;
import com.tradeZen.model.User;
import com.tradeZen.response.PaymentResponse;

public interface PaymentService {

	PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);
	PaymentOrder getPaymentOrderById(Long id) throws Exception;
	boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException;
	
	PaymentResponse createRazorPayPaymentLink(User user,Long amount,Long orderId) throws RazorpayException;
	PaymentResponse createStripePayPaymentLink(User user,Long amount,Long orderId) throws StripeException;
}
