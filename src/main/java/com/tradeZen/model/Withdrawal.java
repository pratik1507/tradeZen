package com.tradeZen.model;

import java.time.LocalDateTime;

import com.tradeZen.domain.WithdrawalStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Withdrawal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private WithdrawalStatus status;
	
	private Long amount;
	
	@ManyToOne
	private User user;
	
	private LocalDateTime date = LocalDateTime.now();

}
