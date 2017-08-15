package com.ex.day2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.ex.pojos.Honda;

public class Serialization {

	static String file = "src/com/ex/files/bytestream.txt";
	
	public static void main(String[] args) {
//		Honda honda = new Honda();
//		honda.setDescription("This honda is amazing. 5/5 would buy again");
//		honda.setModel("some new one");
//		honda.setMpg(10000);
//		
		//writeObject(honda);
		Honda h = (Honda) readObject();
		System.out.println(h.getDescription());
		
	}

	static void writeObject(Object obj) {
		
		try(ObjectOutputStream oos = 
				new ObjectOutputStream(
						new FileOutputStream(file))){
			oos.writeObject(obj);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	static Object readObject() {
		Object obj = null;
		try(ObjectInputStream ios =
				new ObjectInputStream(
						new FileInputStream(file))){
			obj = ios.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return obj;
	}
}
