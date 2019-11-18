import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Channel")
public class Channel extends HttpServlet {

	private static final long serialVersionUID = 1L;

@Override
  public void service(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {

	DBInterface dbi = new DBInterface ();
	
    response.setContentType("text/plain");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().print("Channel\r\n");

  }
}