import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject;
//import javax.mail.Session;
import javax.servlet.http.HttpSession;
import java.sql.Date;


public class Instructor extends User{
	public Instructor(String email, String password, String name) {
		super(email, password, name);
	}
	
	/*
	 *	For instructor, check in should insert a row into the lecture table  
	 *	developer name: 
	 */
	public void checkIn(String keyword, int ratings, double accuracy,int courseID, int instructorID,
							double latitude, double longitude, DBInterface db) {
		// to do 
		ArrayList<Object> array = new ArrayList<Object>();
		  array.add(new Integer(courseID));
		  ResultSet rs = db.makeQuery("select currentLectureNumber from Course where courseID=?", array);
		  try {
		   if (rs.next()) {
			    int currentLecture = rs.getInt("currentLectureNumber");
			    array.remove(0);
			    array.add(new Integer(courseID));
			    array.add(new Integer(instructorID));
			    array.add(new Date(System.currentTimeMillis()));
			    array.add(new Integer(currentLecture));
			    array.add(new String(keyword));
			    array.add(new Double(latitude));
			    array.add(new Double(longitude));
			    array.add(new Double(accuracy));
			    db.makeUpdate("update Lecture set courseID=?, instructorID=?, "
			      + "lectureStartTime=?, lectureNumber=?, keyword=?, lat=?, lon=?, accuracy=? ", array);
			    ArrayList<Object> a = new ArrayList<Object>();
			    a.add(new Integer(currentLecture + 1));
			    a.add(new Integer(courseID));
			    db.makeUpdate("update Course set currentLectureNumber=? where courseID=?", a);
			   }
		   } catch (SQLException e) {
			   e.printStackTrace();
		  }
	}
	
	/*
	 * insert a row into Course table
	 */
	public void addClass(int courseID, String info, DBInterface db) {
		// to do 
	} 
	
	/*
	 * drop the class identified by the courseID
	 */
	public void dropClass(int courseID, DBInterface db) {
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