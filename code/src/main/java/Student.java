import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.lang.Math; 
import java.util.*;

public class Student extends User{
	public Student(String email, String password, String name, int userID) {
		super(email, password, name, userID);
	}
	
	// status: test OK --- Zixuan Zhang
	public boolean checkIn(String keyword, int ratings, double accuracy, int courseID, int instructorID,
							double latitude, double longitude, DBInterface db) {
		// to do 
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(courseID);
		ResultSet rs = db.makeQuery("select * from Lecture where courseID=?", params);
		try {
			if (rs.next()) {
				double instructor_latitude = rs.getDouble("latitude");
				double instructor_longtitude = rs.getDouble("longitude");
				double instructor_accurancy = rs.getDouble("accuracy");
				int currLectureNum = rs.getInt("lectureNumber");
				System.out.println("The lecture num is " + currLectureNum);
				double distance = Math.pow((Math.pow((instructor_latitude - latitude),2)+ Math.pow((instructor_longtitude - longitude),2)),1/2);
				System.out.println(distance);
				boolean status = true;
				if(distance <= instructor_accurancy*1.5 && keyword.equals(rs.getString("keyword"))) {
					status = true;
				}
				else {
					status = false;
				}
				if (status) {// successfully checked in 
					System.out.println("Valid position");
					params = new ArrayList<Object>();
					params.add(courseID);
					params.add(currLectureNum);
					params.add(userID);
					rs =  db.makeQuery("select * from Attendance where courseID=? and lectureNumber=? and studentID = ?", params);
					if (rs.next()) {// already checked in 
						return true;
					}
					ArrayList<Object> paramsToinsert = new ArrayList<Object>();
					paramsToinsert.add(userID);
					paramsToinsert.add(courseID);
					paramsToinsert.add(ratings);
					paramsToinsert.add(true);
					paramsToinsert.add(currLectureNum);// if no show of fail to check in, no record will be inserted
					db.makeUpdate("Insert into Attendance (studentID, courseID, prevLectureRating, prescence, lectureNumber) values (?,?,?,?,?)", paramsToinsert);
					System.out.println("Insert successfully");
					return true;
				}
				return false;

			}
		}catch(SQLException sqle)
		{
	    	 System.out.println (sqle.getMessage());
	    }
		return false;
		
	}
	
	// Status: test OK --- Zixuan Zhang 
	public void askQuestion (String question,DBInterface db, int courseID) {
		// to do 
		String insertQuestion = "INSERT into Question(content, upvoteCount, timeCreated, courseID) values(?,?,?,?)";

		  Calendar cal = Calendar.getInstance();
	
		  ArrayList<Object> values = new ArrayList<Object>();
		  values.add(question);
		  values.add(0);
		  values.add(new Timestamp(cal.getTimeInMillis()));
		  values.add(courseID);
		  //insert into User (userID,...) values (?,?,?)
		  db.makeUpdate(insertQuestion, values);
	}
	
	// status: test OK --Zixuan Zhang
	public boolean addClass(int courseID, String courseName, int numGraceDay, 
			int courseJoinCode, DBInterface db) { //abstract
		courseID=getCourseID(courseJoinCode,db);
		String addClass = "INSERT into Enrollment(studentID, courseID) values(?,?)";
		  ArrayList<Object> values = new ArrayList<Object>();
		  values.add(userID); //in this case the info is the student name
		  values.add(courseID);
		  //db.makeUpdate(addClass, values);
		  if(db.makeUpdate(addClass, values) == -1) {
			  
			  return false;
		  }
		  return true;
	} 

	// status: test OK --Zixuan Zhang
	 public boolean dropClass(int courseID, DBInterface db) { //abstract
		  //DELETE FROM table_name WHERE condition;

		  String addClass = "DELETE FROM Enrollment where courseID=? AND studentID=?";
		  ArrayList<Object> values = new ArrayList<Object>();
		  values.add(courseID); //in this case the info is the student name
		  values.add(userID);
		  if(db.makeUpdate(addClass, values) == -1) {
			  
			  return false;
		  }
		  return true;
		 }
	
	//Returns a course list in the following format
	//
	//
	//
	 //
	 public String getCourseList(DBInterface db)
	 {
		 //SELECT Orders.OrderID, Customers.CustomerName, Orders.OrderDate
		// FROM Orders
		// INNER JOIN Customers ON Orders.CustomerID=Customers.CustomerID;
		 
		 String query = "SELECT Enrollment.studentID, Enrollment.courseID, Course.courseName, Course.courseJoinCode From Course INNER JOIN Enrollment ON Enrollment.courseID=Course.courseID";
			ArrayList<Integer> param = new ArrayList<Integer>();
			//param.add(this.userID);//I changed userID to int because it's int in the DB - Zixuan
			ResultSet rs = db.makeQuery(query, param);
			String courseList = "";
			try {
				while(rs.next()) {
					int id = rs.getInt("studentID");
					if(this.userID == id) {
						courseList += (rs.getString("courseName") + "_");
						courseList += (rs.getString("courseID")+ "_");
						courseList += (rs.getString("courseJoinCode")+ ",");
					}
				}
			} catch (SQLException e) {
				System.out.println("Student#getCourseList - SQLException: " + e.getMessage());
			}
			return courseList;
	 }
	 
	 //status: test OK -- Zixuan Zhang
	public String getQuestionFeed (int courseID, DBInterface db)//seperated by comma
	{
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(courseID); //in this case the info is the student n
		ResultSet rs = db.makeQuery("select * from Question where courseID=? order by upvoteCount desc", params);
		String feed = "";
		try {
			while(rs.next()) {
				int questionID = rs.getInt("questionID");
				String question = rs.getString("content");
				question += ","+String.valueOf(rs.getInt("upvoteCount"));
				feed = feed+question+","+String.valueOf(questionID)+";";
			}
			System.out.println("in getQuestionFeed" + feed);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return feed;
	}
	
	// status: test OK --- Zixuan Zhang
	public void upvoteQuestion(int questionID, DBInterface db) {
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(questionID); //in this case the info is the student n
		int status = db.makeUpdate("update Question set upvoteCount=upvoteCount+1 where questionID=?", params);
		
	}
	
	public int getCourseID(int joinCode, DBInterface db) {
		int courseId=0;
		
		System.out.println("inside of getCourseId");
		
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(joinCode); //in this case the info is the student n
		ResultSet rs = db.makeQuery("select courseID from Course where courseJoinCode=?", params);
		
		try {
			if(rs.next()) {
				courseId=rs.getInt("courseID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return courseId;
		
	}
	public int getGraceDay(int courseID, DBInterface db) {	
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(userID);
		params.add(courseID);
		ResultSet rs = db.makeQuery("select * from Enrollment where studentID=? and courseID=?", params);
		ArrayList<Object> params1 = new ArrayList<Object>();
		params1.add(courseID);
		ResultSet total_graceday = db.makeQuery("select * from Course where courseID=?", params1);
		
		try {
			int num_attendance = 0;
			if (total_graceday.next()) {	
				int total = total_graceday.getInt("numGraceDays");
				int curr = total_graceday.getInt("currentLectureNumber");
				while(rs.next())
				{
					num_attendance++;
				}
				int check_start = curr-num_attendance;
				if(check_start > 0) {
					int result = total - check_start;
					return result -1;
				}
				int result = total - num_attendance;
				return result + 1;
			}
			return -1;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
}
