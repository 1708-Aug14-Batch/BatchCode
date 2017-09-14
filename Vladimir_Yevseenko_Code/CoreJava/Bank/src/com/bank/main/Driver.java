package com.bank.main;

import java.math.BigDecimal;
import java.util.Scanner;

import com.bank.pojos.AccountType;
import com.bank.service.BankService;

public class Driver {
	static Scanner scanner = new Scanner(System.in);
	static BankService service = new BankService();
	
	public static void main(String[] args) {
		boolean running = true;
		
		while (running) {
			System.out.println("\nSelect an option:");
			System.out.println("(1) login, (2) create user");
			switch (scanner.nextLine()) {
				case "1": login(); break;
				case "2": createAcc(); break;
				default: System.out.println("\nInvalid choice.");
			}
		}
	}
	
	
	public static void login() {
		System.out.print("\nEnter email: ");
		String email = scanner.nextLine();
		System.out.print("Enter password: ");
		String pw = scanner.nextLine();
		
		if (service.attemptLogin(email, pw)) {
			System.out.println("\nLogin succesful");
			accountChoices();
		} else {
			System.out.println("\nInvalid email/password");
		}
	}
	
	public static void createAcc() {
		System.out.print("\nEnter first name: ");
		String fn = scanner.nextLine();
		System.out.print("Enter last name: ");
		String ln = scanner.nextLine();
		System.out.print("Enter email: ");
		String email = scanner.nextLine();
		System.out.print("Enter password: ");
		String pw = scanner.nextLine();
		
		if (service.createNewUser(fn, ln, email, pw))
			System.out.println("\nUser successfully created\n");
		else
			System.out.println("\nEmail already in use\n");
	}
	
	public static void accountChoices() {
		boolean running = true;
		while (running) {
			System.out.println("\nSelect an option");
			System.out.println("(1) select existing account (2) create new account (3) Transfer (4) Change info (5) logout");
			switch (scanner.nextLine()) {
				case "1": accountSelect(); break;
				case "2": accountCreate(); break;
				case "3": transfer(); break;
				case "4": changeInfo(); break;
				case "5": running = false; break;
				default: System.out.println("Invalid choice\n");
			}
		}
	}
	
	public static void changeInfo() {
		System.out.println("\nWhich field to change: ");
		System.out.println("(1) First (2) Last (3) Password (4) Cancel");
		String choice = scanner.nextLine();
		switch (choice) {
			case "1": changeFirst(); break;
			case "2": changeLast(); break;
			case "3": changePassword(); break;
			case "4": return;
		}
	}
	
	public static void changeFirst() {
		System.out.print("\nEnter new first name: ");
		service.changeFirst(scanner.nextLine());
		System.out.println("\nFirst name successfully changed");
	}
	
	public static void changeLast() {
		System.out.print("\nEnter new last name: ");
		service.changeLast(scanner.nextLine());
		System.out.println("\nLast name successfully changed");
	}
	
	public static void changePassword() {
		System.out.print("\nEnter new password: ");
		service.changePw(scanner.nextLine());
		System.out.println("\nPassword successfully changed");
	}
	
	public static void accountCreate() {
		System.out.println("\nWhich kind of account?");
		System.out.println("(1) Checking (2) Savings (3) Credit (4) cancel");
		String choice = scanner.nextLine();
		switch (choice) {
			case "1": case "2": case "3": 
			{
				if (service.createNewAccount(AccountType.get(Integer.parseInt(choice))))
					System.out.println("\nAccount successfully created\n");
				else
					System.out.println("\nAccount already exists\n");
			} break;
			case "4": return;
			default: System.out.println("Invalid choice\n");
		}
	}
	
	public static void accountSelect() {
		System.out.println("\nSelect account type");
		System.out.println("(1) Checking (2) Savings (3) Credit (4) Cancel");
		String choice = scanner.nextLine();
		switch (choice) {
			case "1":
			case "2":
			case "3": {
				if (service.selectAccount(AccountType.get(Integer.parseInt(choice))))
					accountActions();
				else
					System.out.println("\nAccount does not exist");
			} break;
			case "4": return;
			default: System.out.println("\nInvalid choice\n");
		}
	}
	
	public static void accountActions() {
		boolean running = true;
		while (running) {
			System.out.println("\nSelect a choice");
			System.out.println("(1) View Balance (2) Deposit (3) Withdraw (4) Close account (5) Exit Account");
			switch (scanner.nextLine()) {
				case "1": displayBalance(); break;
				case "2": deposit(); break;
				case "3": withdraw(); break;
				case "4": closeAcc(); return;
				case "5": running = false; break;
				default: System.out.println("Invalid choice");
			}
		}
	}
	
	public static void closeAcc() {
		service.closeAcc();
		System.out.println("\nAccount successfully closed");
	}
	
	public static void displayBalance() {
		System.out.println("\nCurrent balance is: " + service.getBalance().toPlainString() + "\n");
	}
	
	public static void deposit() {
		System.out.print("\nAmount to deposit: ");
		String amtStr = scanner.nextLine();
		try {
			BigDecimal amt = new BigDecimal(amtStr); 
			service.deposit(amt);
			System.out.println("\nDeposit successful");
		} catch (NumberFormatException ex) {
			System.out.println("\nInvalid input");
		}
	}
	
	public static void withdraw() {
		System.out.print("\nAmount to withdraw: ");
		String amtStr = scanner.nextLine();
		try {
			BigDecimal amt = new BigDecimal(amtStr); 
			if (service.withdraw(amt))
				System.out.println("\nWithdraw successful");
			else
				System.out.println("\nCannot withdraw more than balance");
		} catch (NumberFormatException ex) {
			System.out.println("\nInvalid input\n");
		}
	}
	
	public static void transfer() {
		System.out.println("\nAccount to transfer from");
		System.out.println("(1) Checking (2) Savings (3) Credit (4) Cancel");
		AccountType from = null, to = null;
		switch (scanner.nextLine()) {
			case "1": from = AccountType.CHECKING; break;
			case "2": from = AccountType.SAVINGS; break;
			case "3": from = AccountType.CREDIT; break;
			case "4": return;
			default: System.out.println("\nInvalid input"); return;
		}
		
		System.out.println("\nAccount to transfer to");
		System.out.println("(1) Checking (2) Savings (3) Credit (4) Cancel");
		
		switch (scanner.nextLine()) {
			case "1": to = AccountType.CHECKING; break;
			case "2": to = AccountType.SAVINGS; break;
			case "3": to = AccountType.CREDIT; break;
			case "4": return;
			default: System.out.println("\nInvalid input"); return;
		}
		
		if (!service.hasAccs(from,  to)) {
			System.out.println("\nYou do not have both accounts");
			return;
		}
		
		try {
			System.out.print("\nAmount to transfer: ");
			BigDecimal b = new BigDecimal(scanner.nextLine());
			if (service.transfer(from, to, b))
				System.out.println("\nTransfer successful");
			else
				System.out.println("\nInsufficient funds to transfer");
		} catch(NumberFormatException ex) {
			System.out.println("\nInvalid input");
		}
	}
}