package com.revature.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="instructors")
public class Instructor {

		@Id
		@Column(name = "instructor_id")
		@SequenceGenerator(name="instructor_id_seq", sequenceName="instructor_id_seq")
		@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="instructor_id_seq")
		private int id;
		
		@Column(nullable = false)
		private String firstName;
		
		@Column(nullable = false)
		private String lastName;
		
		@Column(nullable = false)
		private String email;
		
		
		public Instructor() {
			
		}
		
		public Instructor(String firstName, String lastName, String email) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
		}

		public Instructor(int id, String firstName, String lastName, String email) {
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
		}
		
		public int getId() {
			return id;
		}
		
		public String getFirstName() {
			return firstName;
		}
		
		public String getLastName() {
			return lastName;
		}
		
		public String getEmail() {
			return email;
		}
		
		public void setId(int id) {
			this.id = id;
		}
		
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		
		public void setEmail(String email) {
			this.email = email;
		}
		
		
		@Override
		public String toString() {
			return "Instructor { id: " + id + 
					", firstName: " + firstName + 
					", lastName: " + lastName + 
					", email: " + email + " }";
		}
		
}