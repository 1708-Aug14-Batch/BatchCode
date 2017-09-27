package com.ex.day3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.ex.pojos.Student;

public class StudentIO {

	static String filename = "src/com/ex/files/students.txt";

	// Because this is the delimiter used to write/read students this
	// character must not appear in any of the student's String fields
	static String delimiterChar = ":";

	// Write a student to the file using their first name, last name
	// and unique email address
	public boolean addStudent(Student s) {

		if (!(isEmailUnique(s.getEmail()))) {
			System.out.println("Student not added");
			return false;
		}
		
		try(BufferedWriter bw = new BufferedWriter(
				new FileWriter(filename, true))) {

			String text = "";

			text += s.getFirstName() + ":";
			text += s.getLastName() + ":";
			text += s.getEmail() + "\n";

			bw.write(text);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	public Student readStudent(String email) {
		ArrayList<Student> allStudents = getAllStudents();
		
		for (Student stud : allStudents)
			if (stud.getEmail().equalsIgnoreCase(email))
				return stud;
		
		return null;
	}
	
	// Returns true if the student was updated
	public boolean updateStudent(String email, Student newStudent) {
		ArrayList<Student> allStudents = getAllStudents();
		
		if (!(isEmailUnique(newStudent.getEmail()))) {
			System.out.println("Student not updated");
			return false;
		}
		
		for (Student stud : allStudents)
			if (stud.getEmail().equalsIgnoreCase(email)) {
				deleteStudent(stud.getEmail());

				addStudent(newStudent);
				return true;
			}
		
		return false;
	}
	
	public ArrayList<Student> getAllStudents() {
		ArrayList<Student> studentList = new ArrayList<Student>();
		try(BufferedReader br = new BufferedReader(
				new FileReader(filename))) {

			String line = null;
			while((line = br.readLine()) != null) {
				
				Student temp = new Student();
				String[] states = line.split(":");
				
				temp.setFirstName(states[0]);
				temp.setLastName(states[1]);
				temp.setEmail(states[2]);
				
				studentList.add(temp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return studentList;
	}
	
	// Returns true if the student was deleted
	public boolean deleteStudent(String email) {
		ArrayList<Student> allStudents = getAllStudents();
		boolean deleted = false;
		
		try(BufferedWriter bw = new BufferedWriter(
				new FileWriter(filename))) {

			for (Student stud : allStudents)
				if (stud.getEmail().equalsIgnoreCase(email))
					deleted = true;
				else addStudent(stud);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return deleted;
	}
	
	public void deleteAllStudents() {
		try(BufferedWriter bw = new BufferedWriter(
				new FileWriter(filename, false))) {
			
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Returns true if the email is not yet in the system
	private boolean isEmailUnique(String email) {
		ArrayList<Student> allStudents = getAllStudents();
		
		for(Student stud : allStudents)
			if (stud.getEmail().equalsIgnoreCase(email)) {
				System.out.println("Student's email must be unique");
				return false;
			}
		
		return true;
	}
}