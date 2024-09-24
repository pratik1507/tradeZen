package com.tradeZen.service;

import java.util.List;

import com.tradeZen.model.User;
import com.tradeZen.model.Withdrawal;

public interface WithdrawalService {
	
	Withdrawal requestWithdrawal(Long amount, User user);
	Withdrawal proceedWithdrawal(Long withdrawalId, boolean accept) throws Exception;
	List<Withdrawal> getUsersWithdrawalHistory(User user);
	List<Withdrawal> getAllWithdrawalRequest();

}
