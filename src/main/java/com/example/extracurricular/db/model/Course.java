package com.example.extracurricular.db.model;

import java.time.LocalDate;

public final class Course {
	private int id;
	
	private String nameEn;
	
	private String nameUk;
	
	private String topicEn;
	
	private String topicUk;
	
	private LocalDate startDate;
	
	private int durationInDays;
	
	private User teacher;
	
	private int students;
	
	public Course() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getNameUk() {
		return nameUk;
	}

	public void setNameUk(String nameUk) {
		this.nameUk = nameUk;
	}

	public String getTopicEn() {
		return topicEn;
	}

	public void setTopicEn(String topicEn) {
		this.topicEn = topicEn;
	}

	public String getTopicUk() {
		return topicUk;
	}

	public void setTopicUk(String topicUk) {
		this.topicUk = topicUk;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public int getDurationInDays() {
		return durationInDays;
	}

	public void setDurationInDays(int durationInDays) {
		this.durationInDays = durationInDays;
	}
	
	public User getTeacher() {
		return teacher;
	}
	
	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}
	
	public int getStudents() {
		return students;
	}

	public void setStudents(int students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "{Id: " + id + ", nameEn: " + nameEn + ", nameUk: " + nameUk + 
				", topicEn: " + topicEn + ", topicUk: " + topicUk + ", startDate: " + startDate + 
				", durationInDays: " + durationInDays + ", teacher: " + teacher + ", number of students: " + 
				students + "}";
	}
}
