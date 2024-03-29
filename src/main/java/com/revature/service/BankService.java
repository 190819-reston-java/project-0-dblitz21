package com.revature.service;

import com.revature.exception.NegativeAmountException;
import com.revature.exception.OverdraftException;
import com.revature.model.User;
import com.revature.repository.UserDAO;

import java.text.NumberFormat;
import java.util.Locale;

public class BankService {
	
	// Format a number in US currency
	NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

	public void checkBalance(User bankMember) {
		System.out.println("Your balance is currently " + format.format(bankMember.getBalance())
		+ "\n");
	}
	
	public void deposit(User bankMember, double deposit, UserDAO userDAO) {
		if (deposit < 0) {
			throw new NegativeAmountException();
		}
		double oldbalance = bankMember.getBalance();
		bankMember.setBalance(bankMember.getBalance() + deposit);
		userDAO.updateUser(bankMember);
		System.out.println("Previous Balance: " + format.format(oldbalance));
		System.out.println("Deposited: " + format.format(deposit));
		System.out.println("Current Balance: " + format.format(bankMember.getBalance()) + "\n");
	}
	
	public void withdraw(User bankMember, double withdrawal, UserDAO userDAO) {
		if (withdrawal < 0) {
			throw new NegativeAmountException();
		}
		double oldbalance = bankMember.getBalance();
		if (withdrawal > oldbalance) {
			throw new OverdraftException();
		}
		bankMember.setBalance(bankMember.getBalance() - withdrawal);
		userDAO.updateUser(bankMember);
		System.out.println("Previous Balance: " + format.format(oldbalance));
		System.out.println("Withdrew: " + format.format(withdrawal));
		System.out.println("Current Balance: " + format.format(bankMember.getBalance()) + "\n");
	}
}
