


public class Instructor extends User{
	public Instructor(String email, String password, String name) {
		super(email, password, name);
	}
	
	/*
	 *	For instructor, check in should insert a row into the lecture table  
	 *	developer name: 
	 */
	public void checkIn(String keyword, String ratings, double accuracy,
							double latitude, double longitude, DBInterface db) {
		// to do 
	}
	
	/*
	 * insert a row into Course table
	 */
	public void addClass(String courseID, String info, DBInterface db) {
		// to do 
	} 
	
	/*
	 * drop the class identified by the courseID
	 */
	public void dropClass(String courseID, DBInterface db) {
		// to do 
	}
	/*
	 *  get the list of courses that are taught by this instructor
	 */
	public String getCourseList(DBInterface db) {
		// to do 
		return "";
	}
	
	/*
	 *  get questions posted by students so far
	 */
	public String getQuestionFeed () {
		// to do 
		return "";
	}
	
	
}