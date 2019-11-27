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
	  PrintWriter pw;
	  HttpSession session=request.getSession(false); 
	  if (session == null) return;//illegal access
	  switch (methodName) {
	  	case "checkIn"://
	  		String keyword = request.getParameter("keyword");
	  		int ratings = Integer.valueOf(request.getParameter("rating"));
	  		int courseID = Integer.valueOf(request.getParameter("courseID"));
	  		double accuracy = Double.parseDouble(request.getParameter("accuracy"));
			double latitude = Double.parseDouble(request.getParameter("latitude"));
			double longitude = Double.parseDouble(request.getParameter("longitude"));

			User userObj = (User) session.getAttribute("userObj");// get the corresponding user object
			//userObj.checkIn(keyword, ratings, accuracy, courseID, instructorID, latitude, longitude, db);
			/*	EXAMPLE: if you want to call checkIn()  
			 * 		1. get the student/instructor object
			 * 		2. call that function and pass in parameter
			 * 		3. return appropriate messages/json files to frontend
			 *  call appropriate handler function, please add/drop parameters if you see fit
			 */
			
			// this is for development, you should write your own response!!
			pw= response.getWriter();
		    pw.print("checkIn called - to do"); 
	  		break;
	  	case "getCourseList":
	  		pw= response.getWriter();
		    pw.print("getCourseList called - to do"); // send data in json format, done
	  		break;
	  	case "addClass":
	  		pw= response.getWriter();
		    pw.print("addClass called - to do"); // send data in json format, done
	  		break;
	  	case "dropClass":
	  		pw= response.getWriter();
		    pw.print("dropClass called - to do"); // send data in json format, done
	  		break;
	  	case "getQuestionFeed":
	  		pw= response.getWriter();
		    pw.print("getQuestionFeed called -to do"); // send data in json format, done
	  		break;
	  	default:
	  		System.out.println("Method not supported! check letter case?");
	  		break;
	  }
	  
  }
}