package com.revature.exceptions;

public class UnderstandingExceptions {
	public static void main(String[] args) {
		int arr[] = new int[4];
		String s = null;
		try {
			arr[4] = s.indexOf(9);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("ArrayIndexOutOfBoundsException caught");
		} catch (NullPointerException e) {
			System.out.println("NullPointerException caught");
		} finally {
			System.out.println("Finally block");
		}
	}
}
