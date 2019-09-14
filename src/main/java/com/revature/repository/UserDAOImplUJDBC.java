package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.revature.model.User;
import com.revature.utils.ConnectionUtil;
import com.revature.utils.StreamCloser;

public class UserDAOImplUJDBC implements UserDAO {

	@Override
	public User getUser(long id) {
		// TODO Auto-generated method stub
		User user = null;
		
		try (Connection conn = ConnectionUtil.getConnection()) { 
			String query = "SELECT * FROM users WHERE id = ?;";
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setLong(1, id);
				if (stmt.execute()) {
					try (ResultSet resultSet = stmt.getResultSet()) {
						if (resultSet.next()) {
							user = createUserFromRS(resultSet);
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		
		return user;
	}

	private User createUserFromRS(ResultSet resultSet) throws SQLException {
		return new User(
				resultSet.getLong("id"),
				resultSet.getString("username"),
				resultSet.getString("password"),
				resultSet.getString("name"), 
				resultSet.getDouble("balance")
		);
	}

	@Override
	public User getUser(String username) {
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		User user = null;
		try (Connection conn = ConnectionUtil.getConnection()){
			statement = conn.prepareStatement(
					"SELECT * FROM users WHERE username = ?;");
			statement.setString(1, username);
			if(statement.execute()) {
				resultSet =  statement.getResultSet();
				if(resultSet.next()) {
					user = createUserFromRS(resultSet);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			StreamCloser.close(resultSet);
			StreamCloser.close(statement);
		}
		
		return user;
	}

	@Override
	public List<User> getUsers() {
		Statement statement = null;
		ResultSet resultSet = null;
		Connection conn = null;
		
		List<User> users = new ArrayList<User>();
		
		try {
			conn = ConnectionUtil.getConnection();
			conn.createStatement();
			
			statement = conn.createStatement();
			
			resultSet = statement.executeQuery("SELECT * FROM users;");
			
			while (resultSet.next()) {
				users.add(createUserFromRS(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// close all open resources to prevent memory leak
			StreamCloser.close(resultSet);
			StreamCloser.close(statement);
			StreamCloser.close(conn);
		} 
		
		return users;
	}

	@Override
	public boolean createUser(User user) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		String query = "INSERT INTO users VALUES(DEFAULT, ?, ?, ?, ?);";
		
		try {
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getName());
			stmt.setDouble(4, user.getBalance());
			stmt.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			StreamCloser.close(stmt);
			StreamCloser.close(conn);
		}
		return true;
	}

	@Override
	public boolean updateUser(User user) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		final String query = "UPDATE users SET username=?, password=?, name=?, balance=? "
				+ "WHERE id = ?;";
		
		try {
			conn = ConnectionUtil.getConnection();
			stmt = conn.prepareStatement(query);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getName());
			stmt.setDouble(4, user.getBalance());
			stmt.setLong(5, user.getId());
			
			stmt.execute();
			
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		} finally {
			StreamCloser.close(stmt);
			StreamCloser.close(conn);
		}
				
		return true;
	}

	@Override
	public boolean deleteUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
