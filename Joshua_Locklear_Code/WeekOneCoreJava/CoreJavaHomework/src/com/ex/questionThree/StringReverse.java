package com.ex.CoreJavaHomework;

public class StringReverse {
	public static void main(String[] args){
		String myString = "Hello";
		System.out.println(myString);
		for(int i = 0; i< myString.length(); i++){
			myString = myString.substring(1, myString.length()-i)
					+ myString.substring(0,1)
					+ myString.substring(myString.length()-i, myString.length());
		}
		System.out.println(myString);
	}
	
}