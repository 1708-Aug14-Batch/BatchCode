package com.revature.beans;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="courses")
public class Course {
	
	@Id
	@Column(name = "course_id")
	@SequenceGenerator(name="course_id_seq", sequenceName="course_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="course_id_seq")
	private int id;
	
	@Column(nullable = false)
	private String name;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "instructor_id")
	private Instructor instructor;
	
	@Column(nullable = false)
	private String description;
	
	public Course() {
		
	}
	
	public Course(int id, String name, Instructor instructor, String description) {
		this.id = id;
		this.name = name;
		this.instructor = instructor;
		this.description = description;
	}
	
	
	@Override
	public String toString() {
		return "Course { id: " + id + 
				", name: " + name + 
				", instructor: " + instructor + 
				", description: " + description + " }";
	}
}