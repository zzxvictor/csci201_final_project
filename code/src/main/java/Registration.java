import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 


@WebServlet("/Register")
public class Registration extends HttpServlet {
	private DBInterface dbHandle;
	private static final long serialVersionUID = 1L;
	public Registration() { // setup db connections 
		this.dbHandle = new DBInterface();
	}
	

@Override // only support post method 
/*
 * registration function for new user sign up 
 * expected parameter in http request payload:
 * 		email, password, name, userType
 * return:
 * 		json object (stringfied) containing user information that 
 * 		can be used by frontend to dynamically create RosterPage.html
 * Responsible Developer: Zixuan Zhang
 */
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
	String email = request.getParameter("email");
	String password = request.getParameter("password");
	String userType = request.getParameter("userType");
	String name = request.getParameter("name");
	ArrayList<Object> params = new ArrayList<Object>();
	params.add(email);
	ResultSet rs= this.dbHandle.makeQuery("select * from User where email = ?",params);
	JSONObject jo = new JSONObject();
	try {
		if (rs.next()) {// duplicate account name found
			jo.put("success", "false");
			jo.put("message", "account already taken!");
		    PrintWriter pw= response.getWriter();
		    pw.print(jo.toJSONString());
		    return; // done 
		}
	} catch (SQLException e) {}
	// if credential not found, create a new account 
	params = new ArrayList();
	params.add(email);params.add(password);
	params.add(userType);params.add(name);
	int status= this.dbHandle.makeUpdate("insert into User (email,password,userType, name) values (?,?,?,?)", params);
	if (status>=0) {
		HttpSession session=request.getSession(true);  
		jo.put("success", "true");
		jo.put("email", email);
		jo.put("userType",userType);
		jo.put("name", name);
		User user;
		// create a user object
		if (userType.equalsIgnoreCase("student")) {// if the user is a student 
			user = new Student (email, password, name);
			params = new ArrayList<Object>();
			params.add(email);
			rs= this.dbHandle.makeQuery("select * from User where email = ?",params);
			int userID;
			try {
				if (rs.next()) {
					userID = rs.getInt("userID");
					params = new ArrayList<Object>();
					params.add(userID);
					status= this.dbHandle.makeUpdate("insert into Student (studentID) values (?)", params);
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			
		}
		else { // if the user is an instructor 
			user = new Instructor (email, password, name);
			params = new ArrayList<Object>();
			params.add(email);
			rs= this.dbHandle.makeQuery("select * from User where email = ?",params);
			int userID;
			try {
				if (rs.next()) {
					userID = rs.getInt("userID");
					params = new ArrayList<Object>();
					params.add(userID);
					status= this.dbHandle.makeUpdate("insert into Instructor (instructorID) values (?)", params);
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		session.setAttribute("userObj", user); // store user obj in the session variable
		jo.put("courseList", ""); 
	    PrintWriter pw= response.getWriter();
	    pw.print(jo.toJSONString()); // send data in json format, done
	}
	

  }
}