package com.hw.problem15;

public class Test {

	public static void main(String[] args) {
		
		Operations operate = new Operations();
		
		double a = 50.0;
		double b = 70.0;
		
		System.out.println("Add: " + operate.add(a, b));
		System.out.println("Subtrat: " + operate.subtract(a, b));
		System.out.println("Multiply: " + operate.multiply(a, b));
		System.out.println("Divide: " + operate.divide(a, b));



	}

}