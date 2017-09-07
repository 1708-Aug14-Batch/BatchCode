package com.ex.day2;

import java.util.Scanner;

public class Arrays {

	public static void main(String[] args) {
		
		int[] x = {1, 2, 3, 4, 5};
		int [][] twoD = {{13,4,5,}, {123, 6, 8}};
		
		int[][] a = new int [4][5];
		int b [][] = new int[1][1];
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Please enter a number:");
		int input = scan.nextInt();
		for(int i:x){
			System.out.println("A:" + i);
			if(i==3)
				continue;
			System.out.println("B:"+ i);
		}
		
		//if-statements
		if(input%2 == 0 && input %5 == 0){
			System.out.println("your number " + input + "is even");
		}
		else if(input%5 == 0){
			System.out.println("devisible by 5");
		}
		else{
			System.out.println("your number" + input + "is odd");
			
		}
		
		//switch statements
		
		switch(input){
		case(10):
			System.out.println("its 10");
		break;
		case(100):
			System.out.println("its 100");
		break;
		default:
			System.out.println("default");
		}
		
		//while loops
		while(input>10){
			System.out.println();
			--input;
		}
		//do while
		do{
			System.out.println("do things " + input);
			input--;
		}while(input > 3);
		
		int [] fib = new int[25];
		fib[0] = 0;
		fib[1] = 1;
		System.out.println(fib[0]);
		System.out.println(fib[1]);
		for(int i = 2; i < fib.length; i++){
			fib[i] = fib[i-1] + fib[i-2];
			System.out.println(fib[i]);
		}
		//System.out.println(fib);
		



	}

}