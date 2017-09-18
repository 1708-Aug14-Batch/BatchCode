package com.ex.beans;

import javax.persistence.*;

@Entity
@Table(name="STUDENTS")
public class Student {
	
	@Id
	@Column(name="STUDENT_ID")
	@SequenceGenerator(name="STUDENTID_SEQ", sequenceName="STUDENTID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STUDENTID_SEQ")
	private int id;
	
	@Column(nullable = false)
	private String firstname;
	
	@Column(nullable = false)
	private String lastname;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	//ONE TO ONE
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TRANSCRIPT_ID")
	private Transcript transcript;
	
	public Student() {}
	
	public Student(int id, String firstname, String lastname, String email, Transcript transcript) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.transcript = transcript;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Transcript getTranscript() {
		return transcript;
	}

	public void setTranscript(Transcript transcript) {
		this.transcript = transcript;
	}
	
}