package com.ex.day3;

public class Recursion {

	public static void main(String[] args) {
		
	}
	
	public int factorial(int n){
		if(n==1) return 1;
		return n * factorial(n-1);
	}
	
	public int add(int a, int b){
		return a+b;
	}
	
	public int addArgs(int ... nums){
		int sum = 0;
		for(int i : nums){
			sum+= i;
		}
		return sum;
	}
	
}