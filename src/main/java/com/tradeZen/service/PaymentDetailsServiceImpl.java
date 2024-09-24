package com.tradeZen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tradeZen.model.PaymentDetails;
import com.tradeZen.model.User;
import com.tradeZen.repository.PaymentDetailsRepository;

@Service
public class PaymentDetailsServiceImpl implements PaymentDetailsService {
	
	@Autowired
	private PaymentDetailsRepository paymentDetailsRepository;

	@Override
	public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifsc,
			String bankName, User user) {
		PaymentDetails paymentDetails = new PaymentDetails();
		paymentDetails.setAccountNumber(accountNumber);
		paymentDetails.setAccountHolderNumber(accountHolderName);
		paymentDetails.setIfsc(ifsc);
		paymentDetails.setUser(user);
		return paymentDetailsRepository.save(paymentDetails);
	}

	@Override
	public PaymentDetails getUsersPaymentDetails(User user) {
		
		return paymentDetailsRepository.findByUserId(user.getId());
	}

}
