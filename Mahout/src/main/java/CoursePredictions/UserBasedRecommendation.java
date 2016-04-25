package CoursePredictions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.bson.Document;

import Model.RecommendedCourse;
import Model.StudentRecommendation;
import Utility.Constants;
import Utility.MongoLab;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;

public class UserBasedRecommendation {
	UserSimilarity userSimilarity;
	UserNeighborhood neighborhood;
	UserBasedRecommender recommender;
	UserSimilarity similarity;
	DataModel model;

	public static void main(String[] args) throws TasteException {

		UserBasedRecommendation rec = new UserBasedRecommendation();
		rec.configureRecommendationEngine("CourseData.csv");
		StudentRecommendation result = rec.getStudentRecommendation("9980698");
		rec.addStudentRecommendationToMongoDB(result);
	}

	public void configureRecommendationEngine(String filePath) {
		try {
			model = new FileDataModel(new File(filePath));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			similarity = new PearsonCorrelationSimilarity(model);
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		neighborhood = new ThresholdUserNeighborhood(0.75,
				similarity, model);
	}

	private List<RecommendedItem> getRecommendations(String studentId,int recommendationCount) {
		if(model!=null && neighborhood != null && similarity != null && model != null)
		{
			UserBasedRecommender recommender = new GenericUserBasedRecommender(
					model, neighborhood, similarity);
			List<RecommendedItem> recommendations = null;
			try {
				recommendations = recommender.recommend(Long.valueOf(studentId), recommendationCount);
			} catch (TasteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return recommendations;
		}
		else
		{
			System.out.println("Run configure method first.");
			return null;
		}
		
	}
	
	public StudentRecommendation getStudentRecommendation(String studentId)
	{
		studentId = studentId.trim();
		List<RecommendedItem> result = getRecommendations(studentId, Constants.COURSE_RECOMMENDATION_COUNT);
		ArrayList<RecommendedCourse> courses = new ArrayList<RecommendedCourse>();
		StudentRecommendation objRecommendation = new StudentRecommendation();
		for(RecommendedItem item : result)
		{
			String courseId = String.valueOf(item.getItemID());
			String grade = String.valueOf(item.getValue());
			RecommendedCourse course = new RecommendedCourse(courseId, grade);
			courses.add(course);
		}
		objRecommendation.setSjsuId(studentId);
		objRecommendation.setRecommendations(courses);
		return objRecommendation;
	}
	
	public void addStudentRecommendationToMongoDB(StudentRecommendation dataObject)
	{
		Gson gson = new Gson();
		String json = gson.toJson(dataObject);
		BasicDBObject basicDBObject = new BasicDBObject("Student",json);
		Document documentToIndert = Document.parse(json);
		MongoLab mongo = new  MongoLab();
		mongo.addToCollection(documentToIndert, "recommendedCourses");
	}

}
