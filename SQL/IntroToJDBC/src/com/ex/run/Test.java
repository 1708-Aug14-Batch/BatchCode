package com.ex.run;

import com.ex.dao.DAOImpl;

public class Test {

	public static void main(String[] args) {
		
		DAOImpl dao = new DAOImpl();
		int id = dao.getID("stored");
		System.out.println(id);

	}

}
