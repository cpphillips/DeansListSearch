
public class Student {

	private String fName;
	private String lName;
	private boolean isPresidentsList;
	
	public Student(String fn, String ln, boolean pl)
	{
		fName = fn;
		lName = ln;
		isPresidentsList = pl;
	}
	
	public String getFirstName()
	{
		return this.fName;
	}
	
	public String getLastName()
	{
		return this.lName;
	}
	
	public boolean getOnPresList()
	{
		return this.isPresidentsList;
	}
	
}
