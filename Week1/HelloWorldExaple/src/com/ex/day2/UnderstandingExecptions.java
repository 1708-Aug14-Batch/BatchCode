package com.ex.day2;

public class UnderstandingExecptions {

	public static void main(String[] args) throws Exception{
		try{
		int[] arr = new int[4];
		arr[0] = 1;
		arr[4] = 5;
		System.out.println(arr[4]);
		System.out.println("pass first exception");
		
		String str = null;
		System.out.println(str.indexOf('a'));
		System.out.println("pass second exception");
		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println("inside catch block");
			//System.out.println(e.getMessage());
			e.printStackTrace();
		
		}
		catch(NullPointerException e){
			System.out.println("inside catch block");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally{
			System.out.println("inside finally block");
		}
		System.out.println("hey i passed the exection");
		
		
			duckException();
		}
	
	
	static void duckException() throws Exception{
		System.out.println("we are ducking exceptions");
		//throw MyException();
	}

}