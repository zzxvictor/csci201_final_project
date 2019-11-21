import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Channel")
public class Channel extends HttpServlet {
 static final long serialVersionUID = 1L;

@Override
  public void service(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {
	String url = "jdbc:mysql://google/FinalProject?cloudSqlInstance=finalproject-258505:us-central1:final-project-db&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root&password=201FinalProjectRoster";
    response.setContentType("text/plain");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().print("Channel\r\n");
	try {
		 Connection conn = DriverManager.getConnection(url);
	} catch (SQLException e) {
		response.getWriter().print(e.getMessage());;
	}
	
  }
}