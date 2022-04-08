package com.ibm.web.jdbc.model;

public class Course {

	private int id;
	private String courseName;
	private String courseDesc;
	public Course(int id, String courseName, String courseDesc) {
		super();
		this.id = id;
		this.courseName = courseName;
		this.courseDesc = courseDesc;
	}
	public Course(String courseName, String courseDesc) {
		super();
		this.courseName = courseName;
		this.courseDesc = courseDesc;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseDesc() {
		return courseDesc;
	}
	public void setCourseDesc(String courseDesc) {
		this.courseDesc = courseDesc;
	}
	@Override
	public String toString() {
		return "Course [id=" + id + ", courseName=" + courseName + ", courseDesc=" + courseDesc + "]";
	}
	
	
}
