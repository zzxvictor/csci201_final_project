import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DBInterface dbHandle;
	public Login() { // setup db connections 
		this.dbHandle = new DBInterface();
	}
	//
@Override
  public void service(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
	
	String email = request.getParameter("Email");
	String password = request.getParameter("Password");
	ResultSet rs = this.dbHandle.makeQuery("select * from Users where Email=? and Password=?",
			new String[] {email, password});
	String message = "";
	try {
		while (rs.next()) {
			message += rs.getString(1)+"\n";
			message += rs.getString(2)+"\n";
			message += rs.getString(3)+"\n";
			message += rs.getString(4)+"\n";
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    response.getWriter().print(message);

  }
}