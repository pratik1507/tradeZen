package com.tradeZen.service;

import com.tradeZen.model.PaymentDetails;
import com.tradeZen.model.User;

public interface PaymentDetailsService {

	public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifsc, String bankName, User user);
	public PaymentDetails getUsersPaymentDetails(User user);
}
