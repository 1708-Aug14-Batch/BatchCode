package com.ex.test;

import java.util.List;

import com.ex.beans.Instructor;
import com.ex.beans.Students;
import com.ex.dao.HibDAO;

public class MainTest {

	public static void main(String[] args) {
		
		HibDAO dao = new HibDAO();
		Instructor inst = new Instructor();
	
//		inst.setName("Dr.Sanchez");
		
		
		//dao.addSimple(inst);
		
//		Students s = new Students();
//		s.setFirstname("kid");
//		s.setLastname("rock");
//		s.setEmail("rick2@email.com");
//		
//		dao.addStudent(s);
		
		
		List<Students> stuff = dao.criteriaDemo();
		for(Students s: stuff){
			
			System.out.println(s.toString());
		}
		
	

	}

}