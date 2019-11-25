
public class Student extends User{
	public Student(String email, String password, String name) {
		super(email, password, name);
	}
	
	public void checkIn(String keyword, String ratings, double accuracy,
							double latitude, double longitude, DBInterface db) {
		// to do 
	}
	
	public void askQuestion (String question,DBInterface db) {
		// to do 
	}
	
	public void addClass(String courseID, String info, DBInterface db) {
		// to do 
	} 
	
	public void dropClass(String courseID, DBInterface db) {
		// to do 
	}
	
	
	public String getCourseList(DBInterface db) {
		// to do 
		return "";
	}
	
	
}
