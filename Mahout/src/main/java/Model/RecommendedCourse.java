package Model;

public class RecommendedCourse {
	
	private String courseId;
	private String expectedGrade;
	
	public RecommendedCourse(String courseId,String grade)
	{
		this.courseId = courseId;
		this.expectedGrade = grade;
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
