package com.ex.beans;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "STUDENTS")
public class Student {

	@Id
	@Column(name = "STUDENT_ID")
	@SequenceGenerator(name = "STUDENT_ID_SEQ", sequenceName = "STUDENT_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STUDENT_ID_SEQ")
	private int id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false, unique = true)
	private String email;

	@Column
	private LocalDate birthDay;

	@Column
	private String favoriteColor;

	// ONE TO ONE Relationship
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRANSCRIPT_ID")
	private Transcript transcript;

	public Student() {
		// TODO Auto-generated constructor stub
	}

	public Student(int id, String firstName, String lastName, String email, LocalDate birthDay, String favoriteColor,
			Transcript transcript) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.birthDay = birthDay;
		this.favoriteColor = favoriteColor;
		this.transcript = transcript;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}

	public String getFavoriteColor() {
		return favoriteColor;
	}

	public void setFavoriteColor(String favoriteColor) {
		this.favoriteColor = favoriteColor;
	}

	public Transcript getTranscript() {
		return transcript;
	}

	public void setTranscript(Transcript transcript) {
		this.transcript = transcript;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", birthDay=" + birthDay + ", favoriteColor=" + favoriteColor + ", transcript=" + transcript + "]";
	}

}
