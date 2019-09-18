package com.revature.mocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.revature.model.User;
import com.revature.repository.UserDAO;

public class UserDAOMock implements UserDAO {
	
	User george = new User(1, "GW100", "cherrytr33", "George Washington", 100);
	User al = new User(2, "Hamilton1", "mysh0t", "Alexander Hamilton", 50);
	User abe = new User(3, "Lincoln", "honest1", "Abraham Lincoln", 500);
	List<User> bankUsers = new ArrayList<User>(Arrays.asList(george, al, abe));

	@Override
	public User getUser(long id) {
		User user = null;
		for (User u : bankUsers) {
			if (u.getId() == id) {
				user = u;
			}
		}
		
		if (user == null) {
			System.out.println("That user is not registered");
		}
		return user;
	}

	@Override
	public User getUser(String username) {
		User user = null;
		for (User u : bankUsers) {
			if (u.getUsername().equals(username)) {
				user = u;
			}
		}
		
		if (user == null) {
			System.out.println("That user is not registered");
		}
		return user;
	}

	@Override
	public List<User> getUsers() {
		return bankUsers;
	}

	@Override
	public boolean createUser(User user) {
		bankUsers.add(user);
		return true;
	}

	@Override
	public boolean updateUser(User user) {
		for (User u : bankUsers) {
			if (user.getUsername().equals(user.getUsername())) {
				 u.setName(user.getName());
				 u.setBalance(user.getBalance());
				 u.setPassword(user.getPassword());
			}
		}
		return true;
	}

	@Override
	public boolean deleteUser(User user) {
		for (User u : bankUsers) {
			if (user.getUsername().equals(user.getUsername())) {
				bankUsers.remove(u);
			}
		}
		
		return true;
	}

}
