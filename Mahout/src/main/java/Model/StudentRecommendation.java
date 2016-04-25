package Model;

import java.util.ArrayList;

public class StudentRecommendation {
	
	private String studentId;
	private ArrayList<RecommendedCourse> recommendations = new ArrayList<RecommendedCourse>();
	public String getSjsuId() {
		return studentId;
	}
	public void setSjsuId(String sjsuId) {
		this.studentId = sjsuId;
	}
	public ArrayList<RecommendedCourse> getRecommendations() {
		return recommendations;
	}
	public void setRecommendations(ArrayList<RecommendedCourse> recommendations) {
		this.recommendations = recommendations;
	}
	
	
	
}
