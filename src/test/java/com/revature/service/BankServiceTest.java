package com.revature.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.revature.exception.NegativeAmountException;
import com.revature.exception.OverdraftException;
import com.revature.mocks.UserDAOMock;
import com.revature.model.User;
import com.revature.repository.UserDAO;

public class BankServiceTest {
	public static UserDAO userDAO = null;
	public static User user = null;
	public static BankService bankservice = null;
	//private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	//private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	//private final PrintStream originalOut = System.out;
	//private final PrintStream originalErr = System.err;
	
	@Before
	public void setUp() {
		bankservice = new BankService();
		userDAO = new UserDAOMock();
		user = new User(1, "GW100", "cherrytr33", "George Washington", 100);
	   // System.setOut(new PrintStream(outContent));
	   // System.setErr(new PrintStream(errContent));
	}
	
	@After
	public void tearDown() {
		bankservice = null;
		userDAO = null;
		user = null;
	    //System.setOut(originalOut);
	    //System.setErr(originalErr);
	}
	
	
	@Test
	public void checkBalance() {
		//assertEquals("Your balance is currently $100.00", outContent.toString());
	}
	@Test
	public void depositBalanceTest() {
		bankservice.deposit(user, 20, userDAO);
		User testUser = userDAO.getUser(user.getId());
		assertEquals(testUser.getBalance(), 120, 0d);
	}
	
	@Test
	public void withdrawalBalanceTest() {
		bankservice.withdraw(user, 60, userDAO);
		User testUser = userDAO.getUser(user.getId());
		assertEquals(testUser.getBalance(), 40, 0d);
	}
	
	@Test(expected = NegativeAmountException.class)
	public void negativeDeposit() {
		bankservice.deposit(user, -50, userDAO);
	}

	@Test(expected = NegativeAmountException.class)
	public void negativeWithdrawal() {
		bankservice.withdraw(user, -30, userDAO);
	}
	
	@Test(expected = OverdraftException.class)
	public void overdraft() {
		bankservice.withdraw(user, 2000, userDAO);
	}
}
