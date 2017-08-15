package com.ex.day2;

public class StringBuilderAndBuffer {
	
	public static void main(String[] args) {
		String str = "hello";
		StringBuffer buff = new StringBuffer("hello");
		StringBuilder build = new StringBuilder("hello");
		
		System.out.println(str.equals(buff.toString()));
		System.out.println(str.equals(build.toString()));
		System.out.println(buff.toString().equals(build.toString()));
		
		str.concat(" world/");
		buff.append(" world/");
		build.append(" world/");
	}
	
	static String doThings(String str) {
		return str;
	}

}
