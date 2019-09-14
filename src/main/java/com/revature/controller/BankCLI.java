package com.revature.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.revature.model.User;
import com.revature.repository.UserDAO;
import com.revature.repository.UserDAOImplUJDBC;

public class BankCLI {
	private static Scanner sc = new Scanner(System.in);
	private static boolean loggedin = false;
	private static UserDAO userDAO = new UserDAOImplUJDBC();
	private static User bankmember = null;
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
//		userDAO.getUsers().forEach((User u)->{
//			if (u.getUsername().equals(username)) {
//				userexists = true;
//			}
//		});
		
	}
	
	public static void checkPassword(User u) {
		System.out.println("Please type your password!");
		String userinput = sc.nextLine();
		if (userinput.equals(u.getPassword())) {
			loggedin = true;
			System.out.println("Welcome Back " + u.getName() + "!");
		}
		else {
			System.out.println("Invalid Password");
		}
	}

}
