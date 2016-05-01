package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class WrapperClass {

	ArrayList<String> studentIds = new ArrayList<String>();
	public ArrayList<String> getStudentIds() {
		return studentIds;
	}
	public void setStudentIds(ArrayList<String> studentIds) {
		this.studentIds = studentIds;
	}
	public HashMap<String, String> getCourses() {
		return courses;
	}
	public void setCourses(HashMap<String, String> courses) {
		this.courses = courses;
	}
	HashMap<String,String> courses = new HashMap<String,String>();
}
