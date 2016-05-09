package Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Utility.MongoLab;

public class Mapper {
	
	private FileWriter writer = null;
	
	public static void main(String[] args)
	{
		Mapper map = new Mapper();
		map.mapCourseAndQuizzes();
	}
	
	public Mapper()
	{
		try {
			writer = new FileWriter("quizInfo2.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void mapCourseAndQuizzes()
	{
		String csvFile = "quizdata2_1.csv";
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
		HashMap<String,Integer> mapping = new MongoLab().getMappedQuizIds("courseInfo");
		
		try {
			
			while ((line = br.readLine()) != null) {
				String[] newValue = new String[9];
				String[] entry = line.split(cvsSplitBy);
				try{
				String quiz_id = entry[3];
				Integer course_id = mapping.get(quiz_id);
				if(course_id != null)
				{
				newValue[0] = String.valueOf(course_id);
				newValue[1] = entry[0];
				newValue[2] = entry[1];
				newValue[3] = entry[2];
				newValue[4] = entry[3];
				newValue[5] = entry[4];
				newValue[6] = entry[5].replaceAll("\"","");
				newValue[7] = entry[6].replaceAll("\"","");
				line = br.readLine();
				entry = line.split(cvsSplitBy);
				newValue[8] = entry[1].replaceAll("\"","");
				writeToCSV(newValue);
				}
				}
				catch(Exception e)
				{
					//e.printStackTrace();
				}
			}
				// Student student = new Student(gradLevel, department, sjsuId, );

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		 try {
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
		    try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
	}
	private void writeToCSV(String[] values)
	{
		try
		{
		   for(String value : values)
		   {
			   writer.append(value);
			   writer.append(',');
		   }
		   writer.append('\n');
		   
		}
		catch(IOException e)
		{
		     e.printStackTrace();
		} 
		
	}
}
