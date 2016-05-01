package Parser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Model.WrapperClass;

public class CourseCSVParser {
	private static WrapperClass coursesDetails = new WrapperClass();
	
	public static void main(String[] args) {
		new CourseCSVParser()
				.getCourseDetailsFromCSV("SmartCampusCourseData_03292016.csv");
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
}
