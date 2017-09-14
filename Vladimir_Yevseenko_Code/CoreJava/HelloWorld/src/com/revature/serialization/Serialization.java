package com.revature.serialization;

import com.revature.pojos.Honda;

public class Serialization {
	public static void main(String[] args) {
		/*
		 *  I have implemented serialization and de-serialization of a Honda
		 *  so we can do that now
		 */
		Honda h = new Honda("civic", 420, "2017 civic, standard");
		h.serialize("src\\com\\revature\\files\\honda");
		h = null;
		h = Honda.deserialize("src\\com\\revature\\files\\honda");
		System.out.println(h.getModel());
		System.out.println(h.getMpg());
		System.out.println(h.getDescription());
	}
}