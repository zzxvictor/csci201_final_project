import java.io.Serializable;

public abstract class User implements Serializable{
	protected String email;
	//protected DBInterface db;
	protected String password;
	protected String userID;
	protected String name;
	private static final long serialVersionUID = 1l;
	public User (String email,String password, String name) {
		this.email = email;
		this.name = name;
	}
	
	public abstract void addClass(String courseID, String info, DBInterface db);
	public abstract void dropClass(String courseID, DBInterface db);
	public abstract void checkIn(String keyword, String ratings, double accuracy,
			double latitude, double longitude, DBInterface db);
	public abstract String getCourseList(DBInterface db);
	
	
}
