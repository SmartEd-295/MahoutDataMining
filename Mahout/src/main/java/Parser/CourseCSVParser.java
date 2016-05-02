package Parser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;

import Model.CourseMongo;
import Model.StudentRecommendation;
import Model.WrapperClass;
import Utility.MongoLab;

public class CourseCSVParser {
	private static WrapperClass coursesDetails = new WrapperClass();
	
	public static void main(String[] args) {
/*		new CourseCSVParser()
				.getCourseDetailsFromCSV("SmartCampusCourseData_03292016.csv");*/
		new CourseCSVParser().parseCourseNameAndCode();
	}

	public WrapperClass getCourseDetailsFromCSV(String filePath) {
		String csvFile = filePath;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int count = 0;
		HashMap<String, String> courses = new HashMap<String, String>();
		ArrayList<String> ids = new ArrayList<String>();
		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] course = line.split(cvsSplitBy);
				
				String studentId = course[0].replace('"', ' ');
				String courseId = course[3].replace('"', ' ');
				String courseName = course[5].replace('"', ' ');
				studentId = studentId.trim();
				courseId = courseId.trim().replaceFirst("^0+(?!$)", "");
				courseName = courseName.trim();
				/*System.out.println("courseId " + courseId + " , name="
				+ courseName);*/
				ids.add(studentId);
				count++;
				if (!courses.containsKey(courseId)) {
					courses.put(courseId, courseName);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		WrapperClass toReturn = new WrapperClass();
		toReturn.setCourses(courses);
		toReturn.setStudentIds(ids);
		System.out.println("Done count = " + courses.size());
		return toReturn;
	}
	
	public static WrapperClass getCoursesDetails()
	{
		if(coursesDetails == null ||( coursesDetails.getCourses().size() ==0 && coursesDetails.getStudentIds().size() == 0))
		{
			coursesDetails = new CourseCSVParser()
			.getCourseDetailsFromCSV("SmartCampusCourseData_03292016.csv");
			System.out.println("parsing called");
		}
		return coursesDetails;
	}
	
	public void parseCourseNameAndCode()
	{
		String csvFile = "CoursesNames.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int count = 0;
		HashSet<String> names = new HashSet<String>();
		WrapperClass wrapper = CourseCSVParser.getCoursesDetails();
		HashMap<String,String> courses = wrapper.getCourses();
		HashMap<String,String> reverseCourse = new HashMap<String,String>();
		for(String key : courses.keySet())
		{
			String key2 = courses.get(key);
			String value = key;
			reverseCourse.put(key2.trim(), value.trim());
		}
		try {
			br = new BufferedReader(new FileReader(csvFile));
			int i = 1;
			while ((line = br.readLine()) != null) {
				try{
					
				String[] course = line.split(cvsSplitBy);
				String courseName = course[0];
				String[] splitted = courseName.split(" - ");
				String code = splitted[0];
				String name = splitted[1];
				String[] splitted1 = code.split(" ");
				code = splitted1[1];
				name = name.trim();
				name = name.replace('"', ' ');
				code =code.replace('"', ' ');
				if(names.add(name))
				{
					int peoplesotId = 0;
					if(reverseCourse.containsKey(name.trim()))
					{
						peoplesotId = Integer.valueOf(reverseCourse.get(name.trim()));
						System.out.println(reverseCourse.get(name.trim()));
						System.out.println("found " + name + " id " + peoplesotId);
						
					}
					CourseMongo courseObject = new CourseMongo(code.trim(),name.trim(),i++,peoplesotId);
						addCourseMongoToMongoDB(courseObject);
					courseName = courseName.trim();
					System.out.println(name);
					count++;
				}
				}
				catch(ArrayIndexOutOfBoundsException e)
				{
					System.out.println(e.getMessage() + line);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Done count = " +count + " set size" + names.size());
	}
	
	public void addCourseMongoToMongoDB(CourseMongo dataObject)
	{
		Gson gson = new Gson();
		String json = gson.toJson(dataObject);
		Document documentToIndert = Document.parse(json);
		MongoLab mongo = new  MongoLab();
		mongo.addToCollection(documentToIndert, "courseDetails 	");
	}

}
