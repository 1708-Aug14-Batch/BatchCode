package com.hw.problem3;
import java.util.Scanner;

public class ReverseString {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Please enter a string:" + " ");
		String str = scan.nextLine();		
		//reverse(str);
		 

		}
	
	public String  reverse(String str){
		if(str.length()==0 || str.length() == 1) return str;
		
		return str.charAt(str.length()-1) + reverse(str.substring(1,str.length()-1)) 
		  + str.charAt(0);
			
		}
		

	

}
