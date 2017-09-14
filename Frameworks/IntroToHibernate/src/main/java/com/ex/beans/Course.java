package com.ex.beans;

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
@Table(name = "COURSES")
public class Course {

	@Id
	@Column(name = "COURSE_ID")
	@SequenceGenerator(name = "COURSE_ID_SEQ", sequenceName = "COURSE_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="COURSE_ID_SEQ")
	private int id;

	@Column(name = "COURSE_NAME", nullable = false)
	private String name;

    /**
     * fetch: (Optional) Whether the association should be lazily
     * loaded or must be eagerly fetched. The EAGER
     * strategy is a requirement on the persistence provider runtime that
     * the associated entity must be eagerly fetched. The LAZY
     * strategy is a hint to the persistence provider runtime.
     */
	// MANY TO ONE Relationship
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn
	private Instructor instructor;

	@Column
	private String description;

	@Column
	private int numCredits;

	public Course() {
		super();
	}

	public Course(int id, String name, Instructor instructor, String description, int numCredits) {
		super();
		this.id = id;
		this.name = name;
		this.instructor = instructor;
		this.description = description;
		this.numCredits = numCredits;
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

	public int getNumCredits() {
		return numCredits;
	}

	public void setNumCredits(int numCredits) {
		this.numCredits = numCredits;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", instructor=" + instructor + ", description=" + description
				+ ", numCredits=" + numCredits + "]";
	}

}