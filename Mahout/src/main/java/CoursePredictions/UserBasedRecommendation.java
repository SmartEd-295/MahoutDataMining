package CoursePredictions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import Model.CourseMongo;
import Model.RecommendedCourse;
import Model.Student;
import Model.StudentRecommendation;
import Model.WrapperClass;
import Parser.CourseCSVParser;
import Utility.AcademicCareer;
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
		rec.configureRecommendationEngine("CourseDataAll2.csv");
		//rec.addRecommendationResultForAllStudents(rec);
		
		/*StudentRecommendation result = rec.getStudentRecommendation("9980698",AcademicCareer.GRAD);
		rec.addStudentRecommendationToMongoDB(result);*/
		rec.addRecommendationForSpecificStudents(rec, AcademicCareer.GRAD, "CMPE","2162");
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
	
	public StudentRecommendation getStudentRecommendation(String studentId,AcademicCareer level)
	{
		studentId = studentId.trim();
		List<RecommendedItem> result = getRecommendations(studentId, Constants.COURSE_RECOMMENDATION_COUNT);
		ArrayList<RecommendedCourse> courses = new ArrayList<RecommendedCourse>();
		StudentRecommendation objRecommendation = new StudentRecommendation();
		HashMap<Integer,String> codes = CourseMongo.getCourseCodesNames();
		
		for(RecommendedItem item : result)
		{
			String courseId = String.valueOf(item.getItemID());
			String grade = String.valueOf(item.getValue());
			int intCourseId = Integer.valueOf(courseId);
			String code = codes.get(intCourseId);
			if(code != null && code.length() > 0)
			{
				Pattern p = Pattern.compile("[0-9]+");
				Matcher m = p.matcher(code);
				int value = 0;
				while (m.find()) {
				    value = Integer.parseInt(m.group());
				    // append n to list
				}
				System.out.println(level.toString() + " " + AcademicCareer.GRAD.toString() + " " + AcademicCareer.UGRD.toString());
				if((value > 196 && level.toString().equals(AcademicCareer.GRAD.toString())) || (value <= 196 && level.toString().equals(AcademicCareer.UGRD.toString())))
				{
					RecommendedCourse course = new RecommendedCourse(courseId, grade,code);
					courses.add(course);
				}
			}
			
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
	
	public void addRecommendationResultForAllStudents(UserBasedRecommendation rec)
	{
		WrapperClass csvData =CourseCSVParser.getCoursesDetails();
		ArrayList<String> studentIds = csvData.getStudentIds();
		System.out.println(studentIds.get(0) + studentIds.get(1));
		int i =0;
		for(String ids:studentIds)
		{
			StudentRecommendation result = rec.getStudentRecommendation(ids,AcademicCareer.GRAD);
			rec.addStudentRecommendationToMongoDB(result);
			System.out.println("Completed for student id:" + ids);
		}
		
	}

	public void addRecommendationForSpecificStudents(UserBasedRecommendation rec,AcademicCareer gradLevel,String departmentCode,String term)
	{
		List<Student> students = new CourseCSVParser().getStudents(departmentCode, gradLevel, term);
		int count = 0;
		for(Student student : students)
		{
			if(count++ < 10)
			{
				StudentRecommendation recommendation = getStudentRecommendation(student.getStudentId(),student.getGradLevel());
				addStudentRecommendationToMongoDB(recommendation);
			}
		}
	}
}
