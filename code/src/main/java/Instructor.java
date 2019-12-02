import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.simple.JSONArray; 
import org.json.simple.JSONObject;
//import javax.mail.Session;
import javax.servlet.http.HttpSession;
import java.sql.Date;


public class Instructor extends User{
	public Instructor(String email, String password, String name, int userID) {
		super(email, password, name, userID);
	}
	
	/*
	 *	For instructor, check in should insert a row into the lecture table  
	 *	developer name: 
	 */
	public boolean checkIn(String keyword, int ratings, double accuracy,int courseID, int instructorID,
							double latitude, double longitude, DBInterface db) {
		// to do 
		System.out.println("instructor check in called");
		 //Delte all questions from previous lecture upon checkin
		ArrayList<Object> ques_arr = new ArrayList<Object>();
	    ques_arr.add(new Integer(courseID));
	    db.makeUpdate("delete from Question where courseID=?",ques_arr);
		ArrayList<Object> array = new ArrayList<Object>();
		  array.add(new Integer(courseID));
		  ResultSet rs = db.makeQuery("select currentLectureNumber from Course where courseID=?", array);
		  try {
		   if (rs.next()) {
			    System.out.println("lecture id found");
			    int currentLecture = rs.getInt("currentLectureNumber");
			    
			    
			    rs = db.makeQuery("select * from Lecture where courseID=?", array);
			    if (rs.next()) { // remove records from the previous lecture 
			    	db.makeUpdate("delete from Lecture where courseID=?", array);
			    }
			    array.remove(0);
			    array.add(new Integer(courseID));
			    array.add(new Integer(instructorID));
			    Calendar cal = Calendar.getInstance();
			    array.add(new Timestamp(cal.getTimeInMillis()));
			    //array.add(new Date(System.currentTimeMillis()));
			    array.add(new Integer(currentLecture));
			    array.add(new String(keyword));
			    array.add(new Double(latitude));
			    array.add(new Double(longitude));
			    array.add(new Double(accuracy));
			    int status = db.makeUpdate("insert into Lecture (courseID, instructorID,lectureStartTime,"
			    		+ " lectureNumber, keyword,latitude,longitude,accuracy) values (?,?,?,?,?,?,?,?)", array);
			    //int status = db.makeUpdate("update Lecture set courseID=?, instructorID=?, "
			    //  + "lectureStartTime=?, lectureNumber=?, keyword=?, latitude=?, longitude=?, accuracy=? ", array);
			    System.out.println(status);
			    ArrayList<Object> a = new ArrayList<Object>();
			    a.add(new Integer(currentLecture + 1));
			    a.add(new Integer(courseID));
			    System.out.println("send reminder");
			    //EmailReminder.sendReminder("zhangzix@usc.edu", "Victor", "CSCI201");
			    EmailReminder.sendReminder(courseID, db);
			    db.makeUpdate("update Course set currentLectureNumber=? where courseID=?", a);
			    
			    return true;
			   }
		   } catch (SQLException e) {
			   e.printStackTrace();
			   return false;
		  }
		  return false;
	}
	
	/*
	 * insert a row into Course table
	 * status: test OK --Zixuan Zhang
	 */
	public boolean addClass(int courseID, String courseName, int numGraceDay,
			int courseJoinCode, DBInterface db) {
		// to do 
		//System.out.println("In the instructor addclass");
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(courseName);
		params.add(userID);
		params.add(courseJoinCode);
		params.add(numGraceDay);
		params.add(1);
		if(db.makeUpdate("Insert into Course (courseName, instructorID, courseJoinCode, numGraceDays, currentLectureNumber) values (?,?,?,?,?)", params) == -1) {
			  
			  return false;
		  }
		  return true;
	}  
	
	/*
	 * drop the class identified by the courseID
	 */
	public boolean dropClass(int courseID, DBInterface db) {
		// to do 
		System.out.println("In the instructor drop class");
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(courseID);
		//db.makeUpdate("DELETE FROM Course where courseID=?", params);
		if(db.makeUpdate("DELETE FROM Course where courseID=?", params) == -1) {
			  
			  return false;
		  }
		  return true;
	}
	/*
	 *  get the list of courses that are taught by this instructor
	 *  developer name: Kyle Ashcraft + Steve Pierzchala
	 *  
	 */
	public String getCourseList(DBInterface db) {
		String query = "SELECT * FROM Course WHERE instructorID = ?";
		ArrayList<Integer> param = new ArrayList<Integer>();
		param.add(this.userID);// I changed userID to int because it's int in the DB - Zixuan
		ResultSet rs = db.makeQuery(query, param);
		String courseList = "";
		try {
			while(rs.next()) {
				courseList += (rs.getString("courseName") + "_");
				courseList += (rs.getString("courseID")+ "_");
				courseList += (rs.getString("courseJoinCode") + ",");
			}
		} catch (SQLException e) {
			System.out.println("Instructor#getCourseList - SQLException: " + e.getMessage());
		}
		return courseList;
	}
	
	/*
	 *  get questions posted by students so far
	 *  status: test OK -- Zixuan Zhang
	 */
	public String getQuestionFeed (int courseID, DBInterface db)//seperated by comma
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(courseID); //in this case the info is the student n
		ResultSet rs = db.makeQuery("select * from Question where courseID=? order by upvoteCount desc", params);
		String feed = "";
		try {
			while(rs.next()) {
				String question = rs.getString("content");
				question += ":-("+String.valueOf(rs.getInt("upvoteCount"))+":-)";
				feed += question;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return feed;
	}
	public String getCourseStats(int courseID, DBInterface db) {
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(courseID); //in this case the info is the student n
		ResultSet rs = db.makeQuery("select count(*) from Enrollment where courseID=?", params);
		int studentNum = 0;
		try {
			if (rs.next()) {
				studentNum = rs.getInt("count(*)");
				System.out.println(studentNum);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		rs = db.makeQuery("select * from Attendance where courseID=? order by lectureNumber", params);
		String stats =String.valueOf(studentNum)+",";
		try {
			while (rs.next()) {
				int prevRating = rs.getInt("prevLectureRating");
				int lecNum =  rs.getInt("lectureNumber");
				stats += String.valueOf(prevRating)+"_"+String.valueOf(lecNum)+",";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stats;
	}
	
	
}