package com.ex.fileio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



public class Serialization {

	static String file = "src/com/ex/files/bytestream.txt";
	public static void main(String[] args) {
		Honda honda = new Honda();
		writeObject(honda);

		Honda h = (Honda) readObject();
		System.out.println(h.getColor());
	}
	
	static void writeObject(Object o){
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){
			oos.writeObject(o);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static Object readObject(){
		Object obj = null;
		
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))){
			obj = ois.readObject();
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
