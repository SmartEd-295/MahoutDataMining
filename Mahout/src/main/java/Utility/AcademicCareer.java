package Utility;

public enum AcademicCareer {

	GRAD("GRAD"),
	UGRD("UGRD"),
	PGAC("PGAC"),
	UNKNOWN("UNKNOWN");
	
	private String value;
	AcademicCareer(String value)
	{
		this.value = value;
	}
	
	public String toString()
	{
		return value.toString();
	}
	
	public static AcademicCareer getAcademicCareer(String value)
	{
		if(AcademicCareer.GRAD.toString().equalsIgnoreCase(value))
			return AcademicCareer.GRAD;
		else if(AcademicCareer.UGRD.toString().equalsIgnoreCase(value))
			return AcademicCareer.UGRD;
		else if(AcademicCareer.PGAC.toString().equalsIgnoreCase(value))
			return AcademicCareer.PGAC;
		else
			return AcademicCareer.UNKNOWN;
	}
	
}
