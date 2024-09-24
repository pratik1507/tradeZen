package com.tradeZen.service;

import com.tradeZen.domain.VerificationType;
import com.tradeZen.model.ForgotPasswordToken;
import com.tradeZen.model.User;

public interface ForgotPasswordSerivce {
	
	ForgotPasswordToken createToken(User user, String id,
			String otp, VerificationType verificationType, 
			String sendTo);
	
	ForgotPasswordToken findById(String id);
	ForgotPasswordToken findByUser(Long userId);
	void deleteToken(ForgotPasswordToken token);

}
