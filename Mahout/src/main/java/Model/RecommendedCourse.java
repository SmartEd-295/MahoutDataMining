package Model;

import java.util.HashMap;

import Parser.CourseCSVParser;



public class RecommendedCourse {
	
	private String courseId;
	private String expectedGrade;
	private String courseName;
	private String courseCode;
	
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	
	public RecommendedCourse(String courseId,String grade,String code)
	{
		this.courseId = courseId;
		this.expectedGrade = grade;
		WrapperClass wrapper = CourseCSVParser.getCoursesDetails();
		String name = wrapper.getCourses().get(courseId);
		this.courseCode = code;
		this.courseName = name;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getExpectedGrade() {
		return expectedGrade;
	}
	public void setExpectedGrade(String expectedGrade) {
		this.expectedGrade = expectedGrade;
	}
	
}
