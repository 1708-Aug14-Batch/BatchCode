package com.hw.problem9;

import java.util.ArrayList;

public class Prime {

	public static void main(String[] args) {		
		
		ArrayList<Integer> list = new ArrayList<>();
		
		for (int i=2; i<=100; i++){
			list.add(i);
		}
		for (int i=2; i<=10; i++){
			for (int j=i*2; j<=100; j += i){
				list.remove((Object) j);
			}
			
		}
		System.out.println("Prime numbers from 1 to 100 = ");
		for (int n: list){
			System.out.print(n + " ");
		}

	}

}
