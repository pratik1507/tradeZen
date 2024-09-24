package com.tradeZen.service;

import com.tradeZen.domain.VerificationType;
import com.tradeZen.model.User;
import com.tradeZen.model.VerificationCode;

public interface VerificationCodeService {
	
	VerificationCode sendVerificationCode(User user,VerificationType verificationType);
	
	VerificationCode getVerificationCodeById(Long id) throws Exception;
	
	VerificationCode getVerificationCodeByUser(Long userId);
	
	void deleteVerificationCodeById(VerificationCode verificationCode);
	

}
