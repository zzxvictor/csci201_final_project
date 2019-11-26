import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject;
import javax.mail.Session;
import javax.servlet.http.HttpSession;

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
	 *  developer name: Kyle Ashcraft
	 *  NEEDS TESTING
	 */
	public String getCourseList(DBInterface db) {
		String query = "SELECT * FROM Course WHERE instructorID = ?";
		ArrayList<String> param = new ArrayList<String>();
		param.add(this.userID);
		ResultSet rs = db.makeQuery(query, param);
		JSONArray arr = new JSONArray();
		try {
			while(rs.next()) {
				arr.add(rs.getString("courseName"));
			}
		} catch (SQLException e) {
			System.out.println("Instructor#getCourseList - SQLException: " + e.getMessage());
		}
		return arr.toJSONString();
	}
	
	/*
	 *  get questions posted by students so far
	 */
	public String getQuestionFeed () {
		// to do 
		return "";
	}
	
	
}