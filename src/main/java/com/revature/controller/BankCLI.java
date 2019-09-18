package com.revature.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.revature.exception.NegativeAmountException;
import com.revature.exception.OverdraftException;
import com.revature.exception.UserNotLoggedInException;
import com.revature.model.User;
import com.revature.repository.UserDAO;
import com.revature.repository.UserDAOImplUJDBC;
import com.revature.service.BankService;

import org.apache.log4j.Logger;

public class BankCLI {
	private static Scanner sc = new Scanner(System.in);
	private static boolean loggedIn = false;
	private static UserDAO userDAO = new UserDAOImplUJDBC();
	private static User bankMember = null;
	private static BankService bankService = new BankService();
	public static Logger logger = Logger.getLogger(BankCLI.class);
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
			logger.debug("User attempted to enter something other than the recommend options");
			System.out.println("Input Not Recognized \n");
			menu();
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
		// Checks password and logs you in if password matches the password for that
		// user in the database
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
		// Lets the user set up an account
		System.out.println("Thank you for choosing us for your banking needs!");
		System.out.println("Please choose a Username:");
		
		boolean userNameExists = true;
		String username = null;
		
		// checks that the username isn't already in the database and has the user
		// reenter their name if it already exists.
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
		logger.info("New Account created " + newBankMember.getName());
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
		// Service screen menu for logged in users
		if (!loggedIn) {
			logger.fatal("User reached service screen without logging in");
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
			
			try {
				double deposit = sc.nextDouble();
				bankService.deposit(bankMember, deposit, userDAO);
			}  catch(InputMismatchException e) {
				logger.error("User did not enter a number during deposit", e);
				System.out.println("You have to enter a valid number! Please try again. \n");
				System.out.println("What would you like to do? \n");
				sc.nextLine();
				serviceScreen();
			} 
			
			catch(NegativeAmountException e) {
				logger.error("User attempted to deposit a negative amount", e);
				System.out.println("Deposit failed: You must enter a positive amount!\n");
			}
			
			System.out.println("Is there anything else we can help you with today?\n");
			sc.nextLine();
			serviceScreen();
			break;
		case "3" :
			System.out.println("How much money would you like to withdraw?");
			try {
				double withdrawal = sc.nextDouble();
				bankService.withdraw(bankMember, withdrawal, userDAO);
			}   catch(InputMismatchException e) {
				logger.error("User did not enter a number during deposit", e);
				System.out.println("You have to enter a valid number! Please try again. \n");
				System.out.println("What would you like to do? \n");
				sc.nextLine();
				serviceScreen();
			} 
			
			catch(NegativeAmountException e){
				logger.error("User attempted to enter a negative number for withdrawal amount", e);
				System.out.println("Withdrawal failed: You must enter a positive amount!\n");
			}
			catch(OverdraftException e) {
				logger.error("User attempted to withdraw money than in the account", e);
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
			System.out.println("Input Not Recognized \n");
			logger.debug("User attempted to enter something other than the recommend options");
			serviceScreen();
		}
	}

}
