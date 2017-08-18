package com.bank.pojos;

import java.util.Date;

// Manages bank account information
// Has access to every account
// Cannot own an account

public class Clerk extends Person {
	
	public static double MINIMUM_WAGE = 8.85;
	
	private static int lastIdIssued = 0;
	// Each employee has a unique employeeId
	private final int employeeId;
	
	private final String dateHired;
	
	private double hourlyWage;
	
	// Shows whether the employee is currently working for the company
	// Turns false when an employee is let go
	private boolean hired = true;
	
	// Clerks do have administrator privileges
	final boolean admin = true;
	
	public Clerk(Person person) {
		super(person.getSSN(), person.getFirstName(), person.getLastName());

		dateHired = new Date().toString();
		hourlyWage = MINIMUM_WAGE;
		employeeId = lastIdIssued++;
	}
	private Clerk(Person per, int employeeId, String dateHired, double hourlyWage, boolean hired) {
		super(per.getSSN(), per.getFirstName(), per.getLastName());
		
		this.employeeId = employeeId;
		this.dateHired = dateHired;
		this.hourlyWage = hourlyWage;
		this.hired = hired;
	}

	public double getHourlyWage() {
		return hourlyWage;
	}

	public void setHourlyWage(double hourlyWage) {
		this.hourlyWage = hourlyWage;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public String getDateHired() {
		return dateHired;
	}

	public boolean isAdmin() {
		return admin;
	}

	public boolean isHired() {
		return hired;
	}

	public void setHired(boolean hired) {
		this.hired = hired;
	}
	
	public String toString() {
		return super.toString() + ";" + employeeId + ":" + dateHired + ":" + hourlyWage + ":" + hired;
	}
	public static Clerk fromString(String str) {
		String[] splitStr = str.split(";");
		Person per = Person.fromString(splitStr[0]);
		
		String[] splitSplitStr = splitStr[1].split(":");
		
		int employeeId = Integer.parseInt(splitSplitStr[0]);
		String dateHired = splitSplitStr[1];
		double hourlyWage = Double.parseDouble(splitSplitStr[2]);
		boolean hired = Boolean.getBoolean(splitSplitStr[3]);
		
		return new Clerk(per, employeeId, dateHired, hourlyWage, hired);
	}
	
}
