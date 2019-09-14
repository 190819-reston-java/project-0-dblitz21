package com.revature.controller;

import java.util.Arrays;
import java.util.Scanner;

public class BankCLI {
	private static Scanner sc = new Scanner(System.in);
	
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
			break;
		case "3" :
			System.out.println("Thank you for Banking with us!");
			System.exit(0);
			break;
		default:
			System.out.println("Input Not Recognized");
		}
	}

}
