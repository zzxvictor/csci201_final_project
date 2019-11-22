import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 

@WebServlet("/Register")
public class Registration extends HttpServlet {
	private DBInterface dbHandle;
	private static final long serialVersionUID = 1L;
	public Registration() { // setup db connections 
		this.dbHandle = new DBInterface();
	}
	

@Override // only support post 
  public void service(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
	String email = request.getParameter("email");
	String password = request.getParameter("password");
	String userType = request.getParameter("userType");
	ResultSet rs= this.dbHandle.makeQuery("select * from User where email = ?", new String[] {email});
	JSONObject jo = new JSONObject();
	try {
		if (rs.next()) {
			jo.put("success", "false");
			jo.put("message", "account already taken!");
		    PrintWriter pw= response.getWriter();
		    pw.print(jo.toJSONString());
		    return; // done 
		}
	} catch (SQLException e) {}
	
	int status= this.dbHandle.makeUpdate("insert into User (email,password,userType) values (?,?,?)", new String[] {email,password, userType});
	if (status>=0) {
		jo.put("success", "true");
		jo.put("email", email);
	    PrintWriter pw= response.getWriter();
	    pw.print(jo.toJSONString());
	    
	}
	

  }
}