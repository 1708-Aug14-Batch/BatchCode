package com.ex.day3;

import com.ex.day3.NestedClasses.MemberClass;

public class NestedClasses {
/*
 * Java allows us to write classes within
 * other classes, these are called nested classes
 * 
 * Benefits:
 *  - logical grouping of classes only used in one space
 *  - increases encapsulation
 *  - more readable/ maintainable code
 *  
 *  Types:
 *  - static nested: declared static member of another class
 */
	int a = 0;
	static int b = 0;
	
	//static nested class
	static class StaticClass{
		void message(){
			System.out.println("in static nested class");
			System.out.println(b); //only print other static variables
		}
	}
	
	//inner class
	class MemberClass{
		void message(){
			System.out.println("in member class");
			System.out.println(a + b); //can reference both vars
		}
	}
	
	public static void main(String[] args) {
		System.out.println("main method");
		class localClass{
			void message(){
				System.out.println("local method class");
				System.out.println(b); //can only access other static
			}
		}
		
		//start a new thread
		Runnable run = new Runnable(){

			@Override
			public void run() {
				
			}
			
		};
		
		//Anonymous class: on the fly abstract class or interface
		MyInterface anonClass = new MyInterface(){

			@Override
			public void doThings() {
				System.out.println("doing things");
			}

			@Override
			public void anotherThing() {
				System.out.println("another thing done");
			}
			
		};
		
		StaticClass stat = new StaticClass();
		stat.message();
	}
}

interface MyInterface{
	void doThings();
	void anotherThing();
}

class Other{
	int a;
	String message;
	NestedClasses nested = new NestedClasses();
	MemberClass mc = nested.new MemberClass();
	
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
	
	
}