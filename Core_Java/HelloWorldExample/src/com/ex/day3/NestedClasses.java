package com.ex.day3;

public class NestedClasses {
	/*
	 * Java allows us to write classes within other classes,
	 * these are called nested classes
	 * 
	 * Benefits:
	 * 	- logical grouping of classes only used in one place
	 * 	- increases encapsulation
	 *  - more readable and maintainable code
	 *  
	 * Types:
	 *  - static - declared as a static member of another class
	 */
	
	static class StaticClass {
		int x;
		
		void message() {
			System.out.println("Hello and welcome to my static nested class!");
		}
	}
	
	class MemberClass {
		void message() {
			System.out.println("Hello and welcome to my member class");
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Inside main method");
		
		class localClass {
			void message() {
				System.out.println("Inside local class");
			}
		}
		
		StaticClass stat = new StaticClass();
		stat.message();
		
//		MemberClass mem = new MemberClass();
//		mem.message();
	}
	
	MyInterface anonClass = new MyInterface() {
		@Override
		public void doThings() {
			System.out.println("Doing things");
		}

		@Override
		public void doOtherThings() {
			System.out.println("Doing other things!");
		}
	};

}

interface MyInterface{
	void doThings();
	void doOtherThings();
}

class Other {
	
	int a;
	String message;
	
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	NestedClasses nested = new NestedClasses();
//	MemberClass membeer = new MemberClass();
	
}