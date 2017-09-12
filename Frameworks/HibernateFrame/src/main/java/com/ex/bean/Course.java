package com.ex.bean;

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
@Table(name="COURSES")
public class Course {
	
	@Id
	@Column(name="COURSE_ID")
	@SequenceGenerator(name = "COURSE_ID_SEQ", sequenceName="COURSE_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COURSE_ID_SEQ")
	private int id;
	
	@Column(nullable=false)
	private String name;
	
	//1-n
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="INSTRUCTOR_ID")
	private Instructor instructor;
	
	@Column
	private String description;
	
	
	public Course() {
		super();
	}
	public Course(int id, String name, Instructor instructor, String description) {
		super();
		this.id = id;
		this.name = name;
		this.instructor = instructor;
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Instructor getInstructor() {
		return instructor;
	}
	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", instructor=" + instructor + ", description=" + description
				+ "]";
	}
	
	
}
