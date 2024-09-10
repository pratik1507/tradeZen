package com.tradeZen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradeZen.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByEmail(String email);
}
