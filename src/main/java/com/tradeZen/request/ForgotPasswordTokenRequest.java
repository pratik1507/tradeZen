package com.tradeZen.request;

import com.tradeZen.domain.VerificationType;

import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {
	
	private String sendTo;
	private VerificationType verificationType;

}
