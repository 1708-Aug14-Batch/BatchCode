package com.revature.tests;


import java.sql.Date;
import java.util.List;


import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.revature.dao.DBDAO;
import com.revature.pojos.Reimbursement;
import com.revature.pojos.Status;
import com.revature.pojos.User;

public class DBDAOTests {
	
	private static DBDAO dbdao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbdao = new DBDAO();
	}



	@Test
	@Ignore
	public void getAllUsersTest() {
		List<User> list = dbdao.getAllUsers();
		System.out.println("getAllUsersTest()");
		for (User u: list) {
			System.out.printf("%d %s %s %s %s %b\n", u.getId(), u.getFirst(), u.getLast(),
					u.getEmail(), u.getPassword(), u.getIsManager());
		}
	}
	
	@Test
	@Ignore
	public void getAllNonManagersTest() {
		List<User> list = dbdao.getAllNonManagers();
		System.out.println("getAllNonManagersTest()");
		for (User u: list) {
			System.out.printf("%d %s %s %s %s %b\n", u.getId(), u.getFirst(), u.getLast(),
					u.getEmail(), u.getPassword(), u.getIsManager());
		}
	}
	
	@Test
	@Ignore
	public void getAllManagersTest() {
		List<User> list = dbdao.getAllManagers();
		System.out.println("getAllManagersTest()");
		for (User u: list) {
			System.out.printf("%d %s %s %s %s %b\n", u.getId(), u.getFirst(), u.getLast(),
					u.getEmail(), u.getPassword(), u.getIsManager());
		}
	}
	
	@Test
	@Ignore
	public void getUserByIdTest() {
		User u1 = dbdao.getUserById(1);
		User u2 = dbdao.getUserById(3);
		
		System.out.printf("%d %s %s %s %s %b\n", u1.getId(), u1.getFirst(), u1.getLast(),
					u1.getEmail(), u1.getPassword(), u1.getIsManager());
		
		System.out.printf("%d %s %s %s %s %b\n", u2.getId(), u2.getFirst(), u2.getLast(),
					u2.getEmail(), u2.getPassword(), u2.getIsManager());
	}
	
	@Test
	@Ignore
	public void getAllReimburseentsTest() {
		List<Reimbursement> list = dbdao.getAllReimbursements();
		System.out.println("getAllReimbursementTest()");
		for (Reimbursement r: list) {
			System.out.printf("%d %s %s %s %s %s %s %f\n", r.getId(), r.getSubmitter(),
					r.getResolver(), r.getSubmissionDate(), r.getResolutionDate(),
					r.getStatus(), r.getDescription(), r.getAmount());
		}
	}
	
	@Test
	@Ignore
	public void addUserTest() {
		dbdao.addUser(new User("New", "User", "test@email.com", "passWORD", false));
		dbdao.addUser(new User("Other", "NewUser", "other@email.me", "p4ssw0rd", true));
	}
	
	
	@Test
	@Ignore
	public void addReimbursementTest() {
		dbdao.addReimbursement(new Reimbursement(dbdao.getUserById(1),
												dbdao.getUserById(2),
												new Date(1000000),
												new Date(10000000),
												Status.DENIED,
												"description",
												10000));
	
		dbdao.addReimbursement(new Reimbursement(dbdao.getUserById(4),
				dbdao.getUserById(5),
				new Date(1000000),
				new Date(10000000),
				Status.APPROVED,
				"words",
				100020));
	}
	
	
	@Test
	public void attemptLoginTest() {
		User u = dbdao.attemptLogin("yevseenko.vladimir@gmail.com", "password");
		System.out.println("attemptLoginTest()");
		if (u == null)
			System.out.print("login unsuccessful");
		else
			System.out.print("login successful");
	}
}
