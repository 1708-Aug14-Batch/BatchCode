package com.hw.problem12;

public class Problem12 {

	public static void main(String[] args) {
		int n = 1;
		int[] array = new int[100];
		
		for(int i = 0; i < 100; i++){
			array[i] = i+ n; 
			//System.out.println(array[i]);
		}
		for(int j: array){
			if (j%2==0)
				System.out.println(j);
		}
		// TODO Auto-generated method stub

	}
	//
}