package com.tradeZen.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tradeZen.domain.USER_ROLE;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String fullName;
	private String email;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)  // this annotation ensures that the field is only used for input and not for output. 
	private String password;
	
	@Embedded //It is used to group related fields together in a single class for better organization and to simplify the database schema.
	private TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
	
	
	private USER_ROLE role=USER_ROLE.ROLE_CUSTOMER;

}
