package com.tradeZen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tradeZen.model.Order;
import com.tradeZen.model.PaymentOrder;
import com.tradeZen.model.User;
import com.tradeZen.model.Wallet;
import com.tradeZen.model.WalletTransaction;
import com.tradeZen.response.PaymentResponse;
import com.tradeZen.service.OrderService;
import com.tradeZen.service.PaymentService;
import com.tradeZen.service.UserService;
import com.tradeZen.service.WalletService;

@RestController
public class WalletController {
	
	@Autowired
	private WalletService walletService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping("/api/wallet")
	public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		Wallet wallet = walletService.getUserWallet(user);
		return new ResponseEntity<Wallet>(wallet,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/app/wallet/{walletId}/transfer")
	public ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization") String jwt,
			@PathVariable Long walletId,@RequestBody WalletTransaction request)throws Exception{
		User senderUser = userService.findUserProfileByJwt(jwt);
		Wallet receiverWallet = walletService.findWalletById(walletId);
		Wallet wallet = walletService.walletToWalletTransfer(senderUser, receiverWallet, request.getAmount());
		
		return new ResponseEntity<Wallet>(wallet,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/app/wallet/order/{orderId}/pay")
	public ResponseEntity<Wallet> payOrderPayment(@RequestHeader("Authorization") String jwt,
			@PathVariable Long orderId)throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		Order order  = orderService.getOrderById(orderId);
		Wallet wallet = walletService.payOrderPayment(order, user);
		return new ResponseEntity<Wallet>(wallet,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/app/wallet/deposite")
	public ResponseEntity<Wallet> addMoneyToWallet(@RequestHeader("Authorization") String jwt,
			@RequestParam(name = "order_id")Long orderId,
			@RequestParam(name = "payment_id")String paymentId
			)throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		Wallet wallet = walletService.getUserWallet(user);
		PaymentOrder order = paymentService.getPaymentOrderById(orderId);
		
		Boolean status = paymentService.proceedPaymentOrder(order, paymentId);
		
		if(status) {
			wallet = walletService.addBalance(wallet, order.getAmount());
		}
		
		return new ResponseEntity<Wallet>(wallet,HttpStatus.ACCEPTED);
	}
	


}
