package com.ex.day2;

import java.util.Scanner;

public class Array {

	public static void main(String[] args) {
		int[] x = {1,2,3,4};
		int[][] twoDArray = {{1,3,4},{4,5,6}};
		int[][] a = new int[4][5];
		int b [][] = new int[2][2];
		a[0][1]=4;
		Scanner scan = new Scanner (System.in);
		
		for(int i =0; i < x.length; i++){
			System.out.println(x[i]);
			
		}
		
		for(int i:x){
			System.out.println("for each: "+ i);
		}//enhanced for loop
		System.out.println("Please enter a number: ");
		int input = scan.nextInt();
		
		if(input % 2 == 0 && input % 5 == 0){
			
			System.out.println("Your number is" + input + "even");
		}
		else if(input%5 == 0){
			System.out.println("Your Number is odd" + input);
		}
		else{
			System.out.println("Your number is Odd");
		}
		switch (input){
		case 10: System.out.println("Its 10");
		break;
		case 100: System.out.println("100");
		break;
		default:
			System.out.println("Nothing");
		break;
		}
		
		while(input > 10){
			System.out.println(input);
			input--;
			--input;
			//System.out.println("--");
		}
		do{
			System.out.println(input);
			input--;
		}while(input > 3);
	}

}