package com.revature.model;

public class User {
	private long id;
	private String username;
	private String password;
	private String name;
	private double balance;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User(long id, String username, String password, String name, double balance) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.balance = balance;
	}
	
	// To do... get the id from the database, 
	public User(String username, String password, String name) {
		this.balance = 0;
		this.username = username;
		this.password = password;
		this.name = name;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", balance="
				+ balance + "]";
	}
	
	

}
