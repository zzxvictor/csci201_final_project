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
	public void addClass(int courseID, String courseName, int numGraceDay, DBInterface db) {
		// to do 
		//System.out.println("In the instructor addclass");
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(courseName);
		params.add(userID); params.add(numGraceDay);
		params.add(1);
		db.makeUpdate("Insert into Course (courseName, instructorID, numGraceDays, currentLectureNumber) values (?,?,?,?,?)", params);
	} 
	
	/*
	 * drop the class identified by the courseID
	 */
	public void dropClass(int courseID, DBInterface db) {
		// to do 
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(courseID);
		db.makeUpdate("DELETE FROM Course where courseID=?", params);

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
	public String getQuestionFeed (int courseID, DBInterface db)//seperated by comma
	{
		// to do 
		String Questionlist="";
		  String getQuestion = "SELECT * FROM Question WHERE courseID=?";
		  ArrayList<Object> values = new ArrayList<Object>();
		  values.add(courseID); //in this case the info is the student name
		  ResultSet rs = (ResultSet) db.makeQuery(getQuestion, values);
		  try {
		   while (rs.next()) {
		    String QuestionContent = rs.getString("content");
		    Questionlist += QuestionContent;
		    Questionlist +=","; //it will have an extra comma at the end
		    
		   }
		  } catch (SQLException e) {
		   e.printStackTrace();
		  }
		  
		  // to do 
		  return Questionlist;
	}
	
	
}