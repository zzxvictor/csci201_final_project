import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.lang.Math; 
import java.util.*;

public class Student extends User{
	public Student(String email, String password, String name) {
		super(email, password, name);
	}
	
	public void checkIn(String keyword, int ratings, double accuracy, int courseID, int instructorID,
							double latitude, double longitude, DBInterface db) {
		// to do 
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(courseID);params.add(instructorID);
		ResultSet rs = db.makeQuery("select * from Lecture where courseID=? and instructorID=?", params);
		try {
			double instructor_latitude = rs.getDouble("latitude");
			double instructor_longtitude = rs.getDouble("longtitude");
			double instructor_accurancy = rs.getDouble("accuracy");
			int currLectureNum = rs.getInt("lectureNumber");
			double distance = Math.pow((Math.pow((instructor_latitude - latitude),2)+ Math.pow((instructor_longtitude - longitude),2)),1/2);
			boolean status = true;
			if(distance <= instructor_accurancy) {
				status = true;
			}
			else {
				status = false;
			}
			ArrayList<Object> paramsToinsert = new ArrayList<Object>();
			paramsToinsert.add(userID);
			paramsToinsert.add(courseID);
			paramsToinsert.add(ratings);
			paramsToinsert.add(status);
			paramsToinsert.add(currLectureNum);
			db.makeUpdate("Insert into Attendance (studentID, courseID, prevLectureRating, prescence, lectureNumber) values (?,?,?,?,?)", paramsToinsert);
		}catch(SQLException sqle)
		{
	    	 System.out.println (sqle.getMessage());
	    }
		
	}
	
	public void askQuestion (String question,DBInterface db, int courseID) {
		// to do 
		String insertQuestion = "INSERT into Question(content, upvoteCount, timeCreated, courseID) values(?,?,?,?)";
		  
		  Calendar calendar = Calendar.getInstance();
		  java.util.Date currentDate = calendar.getTime();
		  java.sql.Date date = new java.sql.Date(currentDate.getTime());
	
		  ArrayList<Object> values = new ArrayList<Object>();
		  values.add(question);
		  values.add(0);
		  values.add(date);
		  values.add(courseID);
		  //insert into User (userID,...) values (?,?,?)
		  db.makeUpdate(insertQuestion, values);
	}
	
	public void addClass(int courseID, String info, DBInterface db) { //abstract
		  String addClass = "INSERT into Enrollment(studentID, courseID) values(?,?)";
		  ArrayList<Object> values = new ArrayList<Object>();
		  values.add(userID); //in this case the info is the student name
		  values.add(courseID);
		  db.makeUpdate(addClass, values);
		 } 

	
	 public void dropClass(int courseID, DBInterface db) { //abstract
		  //DELETE FROM table_name WHERE condition;

		  String addClass = "DELETE FROM Enrollment where courseID=? AND studentID=?";
		  ArrayList<Object> values = new ArrayList<Object>();
		  values.add(courseID); //in this case the info is the student name
		  values.add(userID);
		  db.makeUpdate(addClass, values);
		 }
	
	
	 public String getCourseList(DBInterface db){//seperated by comma
		  String courselist="";
		  String getCourse = "SELECT * FROM Enrollment WHERE studentID=?";
		  ArrayList<Object> values = new ArrayList<Object>();
		  values.add(userID); //in this case the info is the student name
		  ResultSet rs = (ResultSet) db.makeQuery(getCourse, values);
		  try {
		   while (rs.next()) {
		    String courseName = rs.getString("courseID");
		    courselist += courseName;
		    courselist +=","; //it will have an extra comma at the end
		    
		   }
		  } catch (SQLException e) {
		   e.printStackTrace();
		  }
		  

	
	
}
