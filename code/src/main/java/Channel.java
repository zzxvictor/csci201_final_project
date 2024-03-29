import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
//
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.api.client.util.IOUtils;
//

@WebServlet("/Channel")
public class Channel extends HttpServlet {
	private DBInterface dbHandle;
	private static final long serialVersionUID = 1L;
	public Channel() { // setup db connections 
		this.dbHandle = new DBInterface();
	}
	
//
	/*
	 * backend interface
	 * Responsible Developer: Zixuan Zhang 
	 */
  public void service(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
	  String methodName = request.getParameter("method");
	  
	  System.out.println("\nService requested from channel...\n Method requested:\t" + methodName + "\n");
	  
	  PrintWriter pw;
	  HttpSession session=request.getSession(false); // get existing session variable
	  if (session == null) return;//illegal access!! must log in first!!
	  //System.out.println("Here login success");
	  switch (methodName) {
	  	case "checkIn"://
	  		
	  		System.out.println("\tInside of checkIn...");
	  		System.out.println(request.getQueryString());
	  		
	  		String keyword = request.getParameter("keyword");
	  		System.out.println(keyword);
	  		int ratings = Integer.valueOf(request.getParameter("rating"));
	  		int courseID = Integer.valueOf(request.getParameter("courseID"));
	  		double accuracy = Double.parseDouble(request.getParameter("accuracy"));
			double latitude = Double.parseDouble(request.getParameter("latitude"));
			double longitude = Double.parseDouble(request.getParameter("longitude"));

			User userObj = (User) session.getAttribute("userObj");// get the corresponding user object
			boolean result = userObj.checkIn(keyword, ratings, accuracy, courseID, userObj.getID(), 
						latitude, longitude, this.dbHandle);
			/*	EXAMPLE: if you want to call checkIn()  
			 * 		1. get the student/instructor object
			 * 		2. call that function and pass in parameter
			 * 		3. return appropriate messages/json files to frontend
			 *  call appropriate handler function, please add/drop parameters if you see fit
			 */
			
			// this is for development, you should write your own response!!
			pw= response.getWriter();
			JSONObject jo = new JSONObject();
			jo.put("success",result); // get the list of courses taken/taught by this user	
			pw.print(jo.toJSONString()); // send json data;
			pw.flush();
			System.out.println("check in: " + result);
	  		break;
	  		
	  	case "getCourseList":
	  		User curruserObj = (User) session.getAttribute("userObj");
	  		curruserObj.getCourseList(dbHandle);
	  		pw= response.getWriter();
		    pw.print("getCourseList called - to do"); // send data in json format, done
	  		break;

	  		
	  	case "addClass":
	  		//dynamically creating a random join code for this course
	  		int joinCode = (int)(Math.random() * 1000000);
	  		
	  		System.out.println("Creating a join code for the course...");
	  		System.out.println("\n\tJoin code: " + joinCode);
	  		
	  		System.out.println(request.getQueryString());
	  		
	  		String courseName = request.getParameter("courseName");
	  		int newcourseID = Integer.valueOf(request.getParameter("courseID"));
	  		int numGraceDay = Integer.valueOf(request.getParameter("numGraceDays"));
	  		int joinCode1 = Integer.valueOf(request.getParameter("joinCode"));
	  		User currUserObj = (User) session.getAttribute("userObj");
	  		boolean result_addClass = false;
	  		if(joinCode1 != 1) 
	  		{
	  			result_addClass = currUserObj.addClass(newcourseID, courseName,numGraceDay, joinCode1, dbHandle);}
	  		else 
	  		{
		  		result_addClass = currUserObj.addClass(newcourseID, courseName,numGraceDay, joinCode, dbHandle);
	  		}
	  		pw= response.getWriter();
		    //pw.print("addClass called - to do"); // send data in json format, done
	  		JSONObject jo_addClass = new JSONObject();
		    jo_addClass.put("success",result_addClass);
		    jo_addClass.put("courseList", currUserObj.getCourseList(dbHandle));
		    response.getWriter().print(jo_addClass.toJSONString()); 
	  		break;
	  		
	  		
	  	case "dropClass":
	  		int DropcourseID = Integer.valueOf(request.getParameter("courseID"));
	  		User currUser = (User) session.getAttribute("userObj");
	  		boolean result_dropclass = currUser.dropClass(DropcourseID, dbHandle);
	  		pw= response.getWriter();
		    //pw.print("dropClass called - to do"); // send data in json format, done
	  		JSONObject jo_dropClass = new JSONObject();
		    jo_dropClass.put("success",result_dropclass);
		    jo_dropClass.put("courseList", currUser.getCourseList(dbHandle));
		    response.getWriter().print(jo_dropClass.toJSONString()); 
	  		break;
	  	case "getQuestionFeed":
	  		int currcourseID=Integer.valueOf(request.getParameter("courseID"));
	  		User currInstructor = (User) session.getAttribute("userObj");
	  		String feed = currInstructor.getQuestionFeed(currcourseID, dbHandle);
	  		pw= response.getWriter();
		    pw.print(feed); // send data in string
	  		break;
	  	case "askQuestion":
	  		courseID = Integer.valueOf(request.getParameter("courseID"));
	  		String question = request.getParameter("question");
	  		Student user = (Student) session.getAttribute("userObj");
	  		user.askQuestion(question, this.dbHandle, courseID);
	  		break;
	  	case "upvoteQuestion":
	  		int questionID = Integer.valueOf(request.getParameter("questionID"));
	  		user = (Student) session.getAttribute("userObj");
	  		user.upvoteQuestion(questionID, this.dbHandle);
	  		break;
	  	case "getCourseStats":
	  		courseID = Integer.valueOf(request.getParameter("courseID"));
	  		Instructor instructor = (Instructor) session.getAttribute("userObj");
	  		String stats = instructor.getCourseStats(courseID, this.dbHandle);
	  		pw= response.getWriter();
		    pw.print(stats); // send data in string
	  		break;
	  	case "getGraceDays":
	  		System.out.println("In getGraceDays");
	  		int gracedayID = Integer.valueOf(request.getParameter("courseID"));
	  		System.out.println(gracedayID);
	  		Student user_grace = (Student) session.getAttribute("userObj");
	  		int grace_day = user_grace.getGraceDay(gracedayID, dbHandle);
	  		System.out.println(grace_day);
	  		pw= response.getWriter();
	  		pw.print(grace_day);
	  		break;
	  	default:
	  		System.out.println("Method not supported! check letter case?");
	  		break;
	  }
	  
  }
}