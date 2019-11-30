import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBInterface dbHandle;
	public Login() { // setup db connections 
		this.dbHandle = new DBInterface();
	}



/*
 * login function for user authentication
 * expected parameter in http request payload:
 * 		email, password
 * return:
 * 		json object (stringfied) containing user information that 
 * 		can be used by frontend to dynamically create RosterPage.html
 * Responsible Developer: Zixuan Zhang
 */
	@Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
		
	String email = request.getParameter("email");
	String password = request.getParameter("password");
	System.out.println(email);
	ArrayList<Object> params = new ArrayList<Object>();
	params.add(email);params.add(password);
	ResultSet rs = this.dbHandle.makeQuery("select * from User where Email=? and Password=?",
						params);
	JSONObject jo = new JSONObject();
	try {
		if (!rs.next()) { // if no credential found
			jo.put("success", "false");
			jo.put("message", "credentials not found");
			PrintWriter pw= response.getWriter();
		    response.getWriter().print(jo.toJSONString());
		    return;// failed to login 
		}
		else { // log in success!
			HttpSession session=request.getSession(true); 
			jo.put("success", "true");
			jo.put("email", email);
			String name = rs.getString("name");
			String userType = rs.getString("userType");
			int userID = rs.getInt("userID");
			jo.put("name", name);
			jo.put("userType", userType);
			jo.put("userID", userID);
			User user;
			if (userType.equalsIgnoreCase("instructor"))
				user = new Instructor (email, password, name, userID);
			else
				user = new Student (email, password, name, userID);
			
			session.setAttribute("userObj", user); // store user obj in the session variable
			jo.put("courseList", user.getCourseList(this.dbHandle)); // get the list of courses taken/taught by this user
			PrintWriter pw= response.getWriter();
		    response.getWriter().print(jo.toJSONString()); // send json data
		}
		
	} catch (SQLException e) {}
	
  }
}