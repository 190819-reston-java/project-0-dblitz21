package com.revature;

import com.revature.controller.BankCLI;
import com.revature.model.User;
import com.revature.repository.UserDAO;
import com.revature.repository.UserDAOImplUJDBC;

/** 
 * Create an instance of your controller and launch your application.
 * 
 * Try not to have any logic at all on this class.
 */
public class Main {

	public static void main(String[] args) {
		//BankCLI.menu();
		UserDAO userDAO = new UserDAOImplUJDBC();
		//userDAO.createUser(new User(0L, "AJack7", "1234", "Andrew Jackson", 20));
		userDAO.getUsers().forEach((User u)->{System.out.println(u);});
		
		User al = userDAO.getUser("Hamilton1");
		al.setBalance(10);
		userDAO.updateUser(al);
		//System.out.println(al.getBalance());
		//System.out.println(userDAO).getUser("GW100"));
	}
}
