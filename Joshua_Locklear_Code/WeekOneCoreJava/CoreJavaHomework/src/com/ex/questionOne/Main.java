package com.ex.CoreJavaHomework;

public class Main {
	static void bubbleSort(int[] myArray){
		int x = myArray.length;
		int tempVar = 0;
	
		for(int i=0; i<x; i++){
			for(int j = 1; j< (x-i); j++){
				if(myArray[j-1]>myArray[j]){
					tempVar = myArray[j-1];
					myArray[j-1] = myArray[j];
					myArray[j]=tempVar;
				}
			}
		}
	}
	public static void main(String[] args) {
		int [] myArray = {1,0,5,6,3,2,3,7,9,8,4};
		//BubbleSort sort = new BubbleSort();
		for(int i= 0; i <  myArray.length; i++ ){
			System.out.println(myArray[i]);
		}
		bubbleSort(myArray);
		for(int i= 0; i <  myArray.length; i++ ){
			System.out.println(myArray[i]);
		}	
		

	}

	


}