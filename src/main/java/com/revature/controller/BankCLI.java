package com.revature.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
	
	public static void serviceScreen() {
		if (!loggedIn) {
			//throw new Exception(UserNotLoggedIn);
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
			bankService.deposit(bankMember, deposit, userDAO);
			System.out.println("Is there anything else we can help you with today?\n");
			sc.nextLine();
			serviceScreen();
			break;
		case "3" :
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
