import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.lang.Math; 

public class Student extends User{
	public Student(String email, String password, String name) {
		super(email, password, name);
	}
	
	public void checkIn(String keyword, String ratings, double accuracy, int courseID, int instructorID, int prevLectureRating,
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
			paramsToinsert.add(prevLectureRating);
			paramsToinsert.add(status);
			paramsToinsert.add(currLectureNum);
			db.makeUpdate("Insert into Attendance (studentID, courseID, prevLectureRating, prescence, lectureNumber) values (?,?,?,?,?)", paramsToinsert);
		}catch(SQLException sqle)
		{
	    	 System.out.println (sqle.getMessage());
	    }
		
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
