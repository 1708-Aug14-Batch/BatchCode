package com.ex.day2;

public class Operators {
	
	public static void main(String[] args) { 
		
		int x = 10;
		
		String answer = x > 10 ? x == 10? "equals": "something": "false";
		
		System.out.println(answer);
		
		Arrays ar1 = new Arrays();
		Arrays ar2 = new Arrays();
		
		System.out.println(ar1.num + " " + ar2.num);
		ar1.num++;
		System.out.println(ar1.num + " " + ar2.num);
		ar2.num++;
		System.out.println(ar1.num + " " + ar2.num);
	}
}