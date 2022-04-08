package com.ibm.web.jdbc.model;

public class Syllabus {

	private int id;
	private String syllabusName;
	private String syllabusDesc;
	public Syllabus(int id, String syllabusName, String syllabusDesc) {
		super();
		this.id = id;
		this.syllabusName = syllabusName;
		this.syllabusDesc = syllabusDesc;
	}
	public Syllabus(String syllabusName, String syllabusDesc) {
		super();
		this.syllabusName = syllabusName;
		this.syllabusDesc = syllabusDesc;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSyllabusName() {
		return syllabusName;
	}
	public void setSyllabusName(String syllabusName) {
		this.syllabusName = syllabusName;
	}
	public String getSyllabusDesc() {
		return syllabusDesc;
	}
	public void setSyllabusDesc(String syllabusDesc) {
		this.syllabusDesc = syllabusDesc;
	}
	@Override
	public String toString() {
		return "Syllabus [id=" + id + ", syllabusName=" + syllabusName + ", syllabusDesc=" + syllabusDesc + "]";
	}
	
}
