package Model;

import java.util.HashMap;

import Parser.CourseCSVParser;

public class CourseMongo {

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getCanvasId() {
		return canvasId;
	}
	public void setCanvasId(int canvasId) {
		this.canvasId = canvasId;
	}
	public int getPeoplesoftId() {
		return peoplesoftId;
	}
	public void setPeoplesoftId(int peoplesoftId) {
		this.peoplesoftId = peoplesoftId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	private String code;
	private int canvasId;
	private int peoplesoftId;
	private String name;
	private String description;
	
	public CourseMongo(String code,String name,int canvasId,int peoplesoftId)
	{
		this.name = name;
		this.code = code;
		this.peoplesoftId = peoplesoftId;c
		this.canvasId = canvasId;
	}
	
}
