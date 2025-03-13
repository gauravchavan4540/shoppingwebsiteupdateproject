package com.amazon.ecommerce.Model;

public class LoginReturn {

	int id;
	int accountType;
	String token;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public LoginReturn() {
		super();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LoginReturn(int id, int accountType, String token) {
		super();
		this.id = id;
		this.accountType = accountType;
		this.token = token;
	}

	@Override
	public String toString() {
		return "LoginReturn [id=" + id + ", accountType=" + accountType + ", token=" + token + "]";
	}

}