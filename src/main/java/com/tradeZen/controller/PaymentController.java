package com.tradeZen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.tradeZen.domain.PaymentMethod;
import com.tradeZen.model.PaymentOrder;
import com.tradeZen.model.User;
import com.tradeZen.response.PaymentResponse;
import com.tradeZen.service.PaymentService;
import com.tradeZen.service.UserService;

@RestController
@RequestMapping("/api")
public class PaymentController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PaymentService paymentService;
	
	@PostMapping("/payment/{paymentMethod}/amount/{amount}")
	public ResponseEntity<PaymentResponse> paymentHandler(@PathVariable PaymentMethod paymentMethod,
			@PathVariable Long amount,@RequestHeader("Authorization") String jwt) throws Exception,RazorpayException,StripeException{
				User user = userService.findUserProfileByJwt(jwt);
				PaymentResponse paymentResponse;
				
				PaymentOrder order = paymentService.createOrder(user, amount, paymentMethod);
				if(paymentMethod.equals(PaymentMethod.RAZORPAY)) {
					paymentResponse = paymentService.createRazorPayPaymentLink(user, amount);
				}else {
					paymentResponse = paymentService.createStripePayPaymentLink(user, amount, amount);
				}
				return new ResponseEntity<PaymentResponse>(paymentResponse, HttpStatus.CREATED);
			}
}
