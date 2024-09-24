package com.tradeZen.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tradeZen.domain.VerificationType;
import com.tradeZen.model.ForgotPasswordToken;
import com.tradeZen.model.User;
import com.tradeZen.model.VerificationCode;
import com.tradeZen.request.ForgotPasswordTokenRequest;
import com.tradeZen.request.ResetPasswordRequest;
import com.tradeZen.response.ApiResponse;
import com.tradeZen.response.AuthResponse;
import com.tradeZen.service.EmailService;
import com.tradeZen.service.ForgotPasswordSerivce;
import com.tradeZen.service.UserService;
import com.tradeZen.service.VerificationCodeService;
import com.tradeZen.utils.OtpUtils;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ForgotPasswordSerivce forgotPasswordSerivce;
	
	@Autowired
	private VerificationCodeService verificationCodeService;
	
	@GetMapping("/api/users/profile")
	public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@PostMapping("/api/users/verification/{verificationType}/send-otp")
	public ResponseEntity<String> sendVerificationOtp(@RequestHeader("Authorization") String jwt,
			@PathVariable VerificationType verificationType) throws Exception{
		
		User user = userService.findUserProfileByJwt(jwt);
		
		VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());
		if(verificationCode==null) {
			verificationCode = verificationCodeService.sendVerificationCode(user, verificationType);
		}
		if(verificationType.equals(VerificationType.EMAIL)) {
			emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
		}
		
		
		return new ResponseEntity<String>("Verification OTP sent successfully",HttpStatus.OK);
		
	}
	
	@PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
	public ResponseEntity<User> enableTwoFactorAuthentication(@PathVariable String otp,
			@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		
		VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());
		
		String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL)?
				verificationCode.getEmail():verificationCode.getMobile();
		
		boolean isVerified=verificationCode.getOtp().equals(otp);
		if(isVerified) {
			User updatedUser = userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(), sendTo, user);
			verificationCodeService.deleteVerificationCodeById(verificationCode);
			return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
		}
		throw new Exception("wrong OTP");
	}
	
	@PostMapping("/auth/users/reset-password/send-otp")
	public ResponseEntity<AuthResponse> sendForgotPasswordOtp(@RequestBody ForgotPasswordTokenRequest req) throws Exception{
		
		User user = userService.findUserByEmail(req.getSendTo());
		String otp = OtpUtils.generateOTP();
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString();
		
		ForgotPasswordToken token = forgotPasswordSerivce.findByUser(user.getId());
		
		if(token == null) {
			token = forgotPasswordSerivce.createToken(user, id, otp,req.getVerificationType(), req.getSendTo());
		}
		
		if(req.getVerificationType().equals(VerificationType.EMAIL)) {
			emailService.sendVerificationOtpEmail(user.getEmail(), token.getOtp());
		}
		
		AuthResponse response = new AuthResponse();
		response.setSession(token.getId());
		response.setMessage("Password reset otp send successfully");
		
		return new ResponseEntity<AuthResponse>(response,HttpStatus.OK);
		
	}
	
	@PatchMapping("/auth/users/reset-password/verify-otp")
	public ResponseEntity<ApiResponse> resetPassword(@RequestParam String id,
			@RequestBody ResetPasswordRequest req,@RequestHeader("Authorization") String jwt) throws Exception{
		User user = userService.findUserProfileByJwt(jwt);
		
		ForgotPasswordToken forgotPasswordToken = forgotPasswordSerivce.findById(id);
		
		boolean isVerified = forgotPasswordToken.getOtp().equals(req.getOtp());
		if(isVerified) {
			userService.updatePassword(forgotPasswordToken.getUser(),req.getPassword());
			ApiResponse res = new ApiResponse();
			res.setMessage("password update successfully");
			return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
		}
		throw new Exception("wrong OTP");
	}
}
