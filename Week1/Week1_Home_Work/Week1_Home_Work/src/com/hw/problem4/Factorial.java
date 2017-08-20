package com.hw.problem4;

import java.util.Scanner;

public class Factorial {
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		//Scanner scan = new Scanner(System.in);
		
		System.out.print("Enter a number:" + " ");
			int num = scan.nextInt();		
		int fact = 1;
		
		// not using recursion
	for(int i = 1; i <= num; i++){
			fact *= i;
		}System.out.println(fact);
	}

	public int factorial(int n){
		if(n == 1) return 1;
		return n * factorial(n-1);
	}

}
