package com.revature.beans;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name="transcripts")
public class Transcript {
	
	@Id
	@Column(name = "transcript_id")
	@SequenceGenerator(name="transcript_id_seq", sequenceName="transcript_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="transcript_id_seq")
	private int id;
	
	/*
	 * Transcript to Course is a many to many relationship
	 */
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name = "students_courses_junction", 
				joinColumns = @JoinColumn(name="transcript_id"), 
				inverseJoinColumns = @JoinColumn(name="course_id"))
	private Set<Course> courses;
	
	public Transcript() {
		
	}
	
	public Transcript(int id, Set<Course> courses) {
		this.id = id;
		this.courses = courses;
	}
	
	public int getId() {
		return id;
	}
	
	public Set<Course> getCourses() {
		return courses;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
	
	
	
	@Override
	public String toString() {
		return "Transcript { id: " + id + 
				", courses: " + (courses == null ? "null" : courses) + " }";
	}
}