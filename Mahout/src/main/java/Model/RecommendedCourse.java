package Model;

import Parser.CourseCSVParser;



public class RecommendedCourse {
	
	private String courseId;
	private String expectedGrade;
	private String courseName;
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	
	public RecommendedCourse(String courseId,String grade)
	{
		this.courseId = courseId;
		this.expectedGrade = grade;
		WrapperClass wrapper = CourseCSVParser.getCoursesDetails();
		String name = wrapper.getCourses().get(courseId);
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
