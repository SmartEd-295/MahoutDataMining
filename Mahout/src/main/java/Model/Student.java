package Model;

import Utility.AcademicCareer;

public class Student {

	AcademicCareer gradLevel;
	public AcademicCareer getGradLevel() {
		return gradLevel;
	}

	public void setGradLevel(AcademicCareer gradLevel) {
		this.gradLevel = gradLevel;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	String department;
	String studentId;
	int term;
	
	public Student(AcademicCareer gradLevel,String department,String studentId,Integer termId)
	{
		this.term = term;
		this.gradLevel = gradLevel;
		this.studentId = studentId;
		this.department = department;
	}
}
