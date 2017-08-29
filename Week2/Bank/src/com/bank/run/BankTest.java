package com.bank.run;

import java.util.Scanner;

import com.bank.dao.DAOImpl;
import com.bank.pojos.Account;
import com.bank.pojos.Users;
import com.bank.services.Service;

public class BankTest {
	static Scanner scan = new Scanner(System.in)  ;
	static int option;
	static String email;
	static DAOImpl dao = new DAOImpl();
	static Service ser = new Service();
	static Account acc1 = new Account();
	static double bal = 0.0;
	static int cash;
	static 	Users user;
	static boolean logon = true;
	public static void main(String[] args) {
		//can = new Scanner(System.in);		




		mainMenu();
		System.out.println();
		option = scan.nextInt();
		menu();



	}



	static void mainMenu(){
		System.out.println("-----WELCOME TO THE BANK-----");
		System.out.println();
		System.out.println("Please Select one of the following options:");
		System.out.println("Option 1: Login");
		System.out.println("Option 2: Create an Account");
	}
	static void menu(){
		switch(option){
		case 1:			
			String pass;
			System.out.println("Welcome to the login section: ");
			System.out.println("Enter you email address:");
			email = scan.next();
			System.out.println("Enter your password:");
			pass = scan.next();
			scan.nextLine();			
				Users user = ser.getUser(email, pass);
			System.out.println(user);			
			while(logon){
				loginMenu();
				switch(option=scan.nextInt()){
				case 1:
					acc1  = ser.getAccount(user.getId());
					System.out.println(acc1);
					System.out.println();
					double bal = ser.getbalance(acc1);
					System.out.println("Your Balance is: " + bal );
					break;
				case 2:			
					acc1  = ser.getAccount(user.getId());
					System.out.println(acc1);
					bal = ser.getbalance(acc1);
					System.out.println();					
					System.out.println("How much money would you like to deposit:");
					cash = scan.nextInt();
					int deposit = (int) ser.deposit((int)bal, cash, ser.getAccount(user.getId()).getAccountId());			
					dao.updateBalance(deposit,user.getId(),  acc1.getAccountId());
					acc1  = ser.getAccount(user.getId());
					System.out.println(acc1);
					bal = ser.getbalance(acc1);
					break;
				case 3:
					acc1  = ser.getAccount(user.getId());
					System.out.println(acc1);
					bal = ser.getbalance(acc1);
					System.out.println();
					System.out.println("How much money would you like to withdraw:");
					cash = scan.nextInt();
					int withdraw = (int) ser.withdrawal((int)bal, cash, ser.getAccount(user.getId()).getAccountId());			
					dao.updateBalance(withdraw, user.getId(), acc1.getAccountId());
					acc1  = ser.getAccount(user.getId());
					System.out.println(acc1);
					bal = ser.getbalance(acc1);//login(1);
					break;
				case 4:
					System.out.println("coming soon");
					mainMenu();
					break;
				case 5:
					logon = false;
					System.out.println("You are now Logged out.");			

				}


			}
			break;
		case 2:
			System.out.println("Welcome New User. Please create a user acount:");
			System.out.println("Please enter your First Name:");
			String fn = scan.next();
			System.out.println("Please enter your Lasty Name:");
			String ln = scan.next();
			System.out.println("Please enter your Email:");
			email = scan.next();
			System.out.println("Please enter your Password:");
			String password = scan.next();
			int user1 = ser.addUser(fn, ln, email, password);
			System.out.println("Thank you creating your USER ACCOUNT.\n Your User ID is: " +user1);
			System.out.println("What type of account do you want to create" + "\n 1: Checking" +"\n 2: Savings"+ "\n 3: Credit Card");
			int type = scan.nextInt();
			Account acc = ser.createAccount(user1, type);
			System.out.println();
			if(type == 1)
				System.out.println("Thank you  for creating a Checking Account");
			else if(type == 2)
				System.out.println("Thank you for creating a Savings Account");
			else if(type == 3)
				System.out.println("Thank you for applying for a Credit Card");
			else
				System.out.println("Invalid Input");
			System.out.println("would you like to login you to your account");
			System.out.println("1: Yes");
			System.out.println("2: No");
			int n = scan.nextInt();
			if(n==1)
				mainMenu();
			System.out.println();
			option = scan.nextInt();
			menu();
			break;
		default:

		}			
	}
	public static void loginMenu(){
		System.out.println("Select one of the following");
		System.out.println("1: Get balance");
		System.out.println("2: Deposit");
		System.out.println("3: Withdraw");
		System.out.println("4: Transfer Funds");
		System.out.println("5: Logout");
	} 



}
