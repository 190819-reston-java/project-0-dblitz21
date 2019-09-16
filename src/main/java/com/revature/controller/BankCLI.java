package com.revature.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.revature.exception.NegativeAmountException;
import com.revature.exception.OverdraftException;
import com.revature.exception.UserNotLoggedInException;
import com.revature.model.User;
import com.revature.repository.UserDAO;
import com.revature.repository.UserDAOImplUJDBC;
import com.revature.service.BankService;

public class BankCLI {
	private static Scanner sc = new Scanner(System.in);
	private static boolean loggedIn = false;
	private static UserDAO userDAO = new UserDAOImplUJDBC();
	private static User bankMember = null;
	private static BankService bankService = new BankService();
	public static void menu() {
		Arrays.asList(
			"Welcome to the Virtual Bank!",
			"Select an Option",
			"1 : Register",
			"2 : Login",
			"3 : Exit"
		).forEach((String s)->{System.out.println(s);});
		
		String userInput = sc.nextLine();
		
		switch(userInput) {
		case "1" :
			register();
			break;
		case "2" :
			login();
			break;
		case "3" :
			System.out.println("Thank you for Banking with us!");
			System.exit(0);
			break;
		default:
			System.out.println("Input Not Recognized");
		}
	}
	
	public static void login() {
		System.out.println("Please Enter Your Username!");
		String username = sc.nextLine();
		boolean userexists = false;
		User checkuser = null;
		List<User> userlist = userDAO.getUsers();
		for (User u : userlist) {
			if (u.getUsername().equals(username)) {
				userexists = true;
				checkuser = u;
				
			}
		}
		
		if (userexists) {
			checkPassword(checkuser);
		}
		
		else {
			System.out.println("That username is not registered!");
		}
		
	}
	
	public static void checkPassword(User u) {
		System.out.println("Please type your password!");
		String userinput = sc.nextLine();
		if (userinput.equals(u.getPassword())) {
			loggedIn = true;
			bankMember = u;
			System.out.println("Welcome Back " + u.getName() + "!\n");
			System.out.println("How can we help you today?");
			serviceScreen();
		}
		else {
			System.out.println("Invalid Password");
		}
	}
	
	public static void register() {
		System.out.println("Thank you for choosing us for your banking needs!");
		System.out.println("Please choose a Username:");
		
		boolean userNameExists = true;
		String username = null;
		while (userNameExists) {
			username = sc.nextLine();
			userNameExists = userNameUnavailable(username);
		}
		System.out.println("Please choose a password:");
		String password  =  sc.nextLine();
		System.out.println("Please Enter Your Name:");
		String name = sc.nextLine();
		
		User newBankMember = new User(username, password, name);
		userDAO.createUser(newBankMember);
		System.out.println("Great " + newBankMember.getName() + "! Your account is all setup.");
	}
	
	
	public static boolean userNameUnavailable(String username) {
		// Checks if that username is already in the database and lets you know if it is
		// unavailable to use.
		List<User> userlist = userDAO.getUsers();
		for (User user : userlist) {
			if (user.getUsername().equals(username)) {
				System.out.println("That username already exists! Please enter another.");
				return true;
			}
		}
		return false;
	}
	
	public static void serviceScreen() throws NegativeAmountException, OverdraftException {
		if (!loggedIn) {
			throw new UserNotLoggedInException();
		}
		Arrays.asList(
				"Select an Option",
				"1 : Check Balance",
				"2 : Deposit",
				"3 : Withdraw Money",
				"4 : Exit"
		).forEach((String s)->{System.out.println(s);});
		
		String userInput = sc.nextLine();
		
		switch(userInput) {
		case "1" :
			bankService.checkBalance(bankMember);
			System.out.println("Is there anything else we can help you with today?\n");
			serviceScreen();
			break;
		case "2" :
			System.out.println("How much money would you like to deposit?");
			double deposit = sc.nextDouble();
			try {
				bankService.deposit(bankMember, deposit, userDAO);
			} catch(NegativeAmountException e) {
				System.out.println("Deposit failed: You must enter a positive amount!\n");
			}
			
			System.out.println("Is there anything else we can help you with today?\n");
			sc.nextLine();
			serviceScreen();
			break;
		case "3" :
			System.out.println("How much money would you like to withdraw?");
			double withdrawal = sc.nextDouble();
			try {
				bankService.withdraw(bankMember, withdrawal, userDAO);
			}
			catch(NegativeAmountException e){
				System.out.println("Withdrawal failed: You must enter a positive amount!\n");
			}
			catch(OverdraftException e) {
				System.out.println("Withdrawal failed: There isn't enough money in your account" 
					+ " to withdraw that amount!\n");
			}
			System.out.println("Is there anything else we can help you with today?\n");
			sc.nextLine();
			serviceScreen();
			break;
		case "4" :
			System.out.println("Thank you for Banking with us!");
			System.exit(0);
			break;
		default:
			System.out.println("Input Not Recognized");
		}
	}

}
