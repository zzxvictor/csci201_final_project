import java.io.Serializable;

public abstract class User implements Serializable{
	protected String email;
	protected String password;
	protected int userID;
	protected String name;
	private static final long serialVersionUID = 1l; 
	public User (String email,String password, String name, int userID) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.userID = userID;
	}
	
	/*
	 * abstract methods that must be implemented in children classes
	 */
	public abstract void addClass(int courseID, String courseName, int numGraceDay, DBInterface db);
	public abstract void dropClass(int courseID, DBInterface db);
	public abstract boolean checkIn(String keyword, int ratings, double accuracy,int courseID, int instructorID,
			double latitude, double longitude, DBInterface db);
	public abstract String getCourseList(DBInterface db);
	public abstract String getQuestionFeed (int courseID, DBInterface db);
	public int getID() {
		return userID;
	}
}
