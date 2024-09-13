package com.trading.crypto.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trading.crypto.domians.USER_ROLE;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	private String mobile;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

	@Embedded
	private TwoFactorAuth twoFactorAuth = new TwoFactorAuth();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public USER_ROLE getRole() {
		return role;
	}

	public void setRole(USER_ROLE role) {
		this.role = role;
	}

	public TwoFactorAuth getTwoFactorAuth() {
		return twoFactorAuth;
	}

	public void setTwoFactorAuth(TwoFactorAuth twoFactorAuth) {
		this.twoFactorAuth = twoFactorAuth;
	}
}