package com.revature.repository;

import java.util.List;
import com.revature.model.User;

public interface UserDAO {
	User getUser(long id);
	User getUser(String username);
	
	List<User> getUsers();
	boolean createUser(User user);
	
	boolean updateUser(User user);
	
	boolean deleteUser(User user);
}
