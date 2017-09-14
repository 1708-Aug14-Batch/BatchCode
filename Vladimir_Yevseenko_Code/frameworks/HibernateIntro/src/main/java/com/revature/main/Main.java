package com.revature.main;

import java.util.List;

import com.revature.beans.Instructor;
import com.revature.beans.Student;
import com.revature.dao.HibernateDAO;

public class Main {
	public static void main(String[] args) {
		HibernateDAO hibernateDAO = new HibernateDAO();
		
		/*Student student = new Student("Vladimir", "Yevseenko",
				"yevseenko.vladimir@gmail.com"); */
		
		Instructor instructor = new Instructor("Paul", "Fodor", "pfodor@cs.stonybrook.edu");
		
		for (int i=0; i<5; i++) {
			Student student = new Student("first" + i, "last" + i, "email" + i);
			hibernateDAO.addStudent(student);
			System.out.println(student);
		}
		
		hibernateDAO.deleteStudentByIdCriteria(50);
		
		List<Student> students = hibernateDAO.getAllStudentsByCriteria();
		students.stream().forEach(student -> System.out.println(student));
		
		students = hibernateDAO.getAllStudentsQuery();
		students.stream().forEach(student -> System.out.println(student));
		
		students = hibernateDAO.getAllStudentsByCriteriaQuery();
		students.stream().forEach(student -> System.out.println(student));
	}
}