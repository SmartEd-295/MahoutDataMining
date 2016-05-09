package Parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.bson.Document;

import Model.CourseMongo;
import Model.Student;
import Model.WrapperClass;
import Utility.AcademicCareer;
import Utility.MongoLab;

import com.google.gson.Gson;

public class CourseCSVParser {
	private static WrapperClass coursesDetails = new WrapperClass();

	public static void main(String[] args) {
		/*
		 * new CourseCSVParser()
		 * .getCourseDetailsFromCSV("SmartCampusCourseData_03292016.csv");
		 */
		ArrayList<Student> students = new CourseCSVParser().getStudents("CMPE", AcademicCareer.UGRD, "2162");
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
				/*
				 * System.out.println("courseId " + courseId + " , name=" +
				 * courseName);
				 */
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

	public static WrapperClass getCoursesDetails() {
		if (coursesDetails == null
				|| (coursesDetails.getCourses().size() == 0 && coursesDetails
						.getStudentIds().size() == 0)) {
			coursesDetails = new CourseCSVParser()
					.getCourseDetailsFromCSV("SmartCampusCourseData_03292016.csv");
			System.out.println("parsing called");
		}
		return coursesDetails;
	}

	public List<CourseMongo> getCourseCodeAndName() {
		String csvFile = "CoursesNames.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int count = 0;
		HashSet<String> names = new HashSet<String>();
		WrapperClass wrapper = CourseCSVParser.getCoursesDetails();
		HashMap<String, String> courses = wrapper.getCourses();
		HashMap<String, String> reverseCourse = new HashMap<String, String>();
		ArrayList<CourseMongo> list = new ArrayList<CourseMongo>();
		for (String key : courses.keySet()) {
			String key2 = courses.get(key);
			String value = key;
			reverseCourse.put(key2.trim(), value.trim());
		}
		try {
			br = new BufferedReader(new FileReader(csvFile));
			int i = 1;
			while ((line = br.readLine()) != null) {
				try {

					String[] course = line.split(cvsSplitBy);
					String courseName = course[0];
					String[] splitted = courseName.split(" - ");
					String code = splitted[0];
					String name = splitted[1];
					String[] splitted1 = code.split(" ");
					code = splitted1[1];
					name = name.trim();
					name = name.replace('"', ' ');
					code = code.replace('"', ' ');
					if (names.add(name)) {
						int peoplesotId = 0;
						if (reverseCourse.containsKey(name.trim())) {
							peoplesotId = Integer.valueOf(reverseCourse
									.get(name.trim()));

						}
						CourseMongo courseObject = new CourseMongo(code.trim(),
								name.trim(), i++, peoplesotId);
						list.add(courseObject);
						courseName = courseName.trim();
						count++;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
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
		return list;
	}
	public void addCourseCodeAndNameToMongo()
	{
		ArrayList<CourseMongo> list = (ArrayList<CourseMongo>) getCourseCodeAndName();
		for(CourseMongo obj : list)
			addCourseMongoToMongoDB(obj);
	}
	public void addCourseMongoToMongoDB(CourseMongo dataObject) {
		Gson gson = new Gson();
		String json = gson.toJson(dataObject);
		Document documentToIndert = Document.parse(json);
		MongoLab mongo = new MongoLab();
		mongo.addToCollection(documentToIndert, "courseDetails 	");
		
	}

	public ArrayList<Student> getStudents(String departmentCode,
			AcademicCareer gradLevel, String termEnrolled) {
		String csvFile = "SmartCampusStudentData_03292016_2.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int count = 0;
		ArrayList<Student> students = new ArrayList<Student>();
		try {
			br = new BufferedReader(new FileReader(csvFile));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = 1;
		
		try {
			while ((line = br.readLine()) != null) {

				String[] studentEntry = line.split(cvsSplitBy);
				String sjsuId = studentEntry[0].replace('"', ' ').trim();
				sjsuId = sjsuId.trim().replaceFirst("^0+(?!$)", "");
				String term = studentEntry[4].replace('"', ' ').trim();
				String level = studentEntry[5].replace('"', ' ').trim();
				String department = studentEntry[7].replace('"', ' ').trim();
				if (AcademicCareer.getAcademicCareer(level).equals(gradLevel)
						&& termEnrolled.equalsIgnoreCase(term)
						&& department.contains(departmentCode)) {
					Student student = new Student(gradLevel, departmentCode,
							sjsuId, Integer.valueOf(term));
					students.add(student);
				}
				// Student student = new Student(gradLevel, department, sjsuId, );

			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(students.size());
		return students;
	}
	
	public ArrayList<String> getQuizIds()
	{
		String csvFile = "quizids.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int count = 0;
		ArrayList<String> ids = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(csvFile));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = 1;
		
		try {
			while ((line = br.readLine()) != null) {

				String[] entry = line.split(cvsSplitBy);
				String id = entry[0];
				ids.add(id);
				System.out.println(id);
				}
				// Student student = new Student(gradLevel, department, sjsuId, );

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ids;
	}
}
