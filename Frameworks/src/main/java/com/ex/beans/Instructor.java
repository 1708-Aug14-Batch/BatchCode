package com.ex.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="INSTRUCTOR")
public class Instructor {
	@Id
	@Column(name="INSTRUCTOR_ID")
	@SequenceGenerator(name="INSTRUCTOR_ID_SEQ", sequenceName="INSTRUCTOR_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INSTRUCTOR_ID_SEQ")
	private int id;
	
	@Column(name="INSTRUCTOR_NAME")
	private String name;
	
	
	public Instructor() {}
	
	
	public Instructor(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	

}