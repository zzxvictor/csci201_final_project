import java.sql.Connection;
import java.sql.*;
import java.sql.SQLException;

import javax.sql.DataSource;
import com.zaxxer.hikari.*;
import com.zaxxer.hikari.HikariConfig;
public class DBInterface {
	private Connection conn ;
	private String url = "jdbc:mysql://google/FinalProject?cloudSqlInstance=finalproject-258505:us-central1:final-project-db&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root&password=201FinalProjectRoster";
 //
	//private String url = "jdbc:mysql://google/lab?cloudSqlInstance=nimble-sweep-255322:us-central1:lab8"+
	//private String url =   "jdbc:mysql://google/FinalProject?cloudSqlInstance=finalproject-258505:us-central1:final-project-db&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root&password=201FinalProjectRoster";
	public DBInterface() {
		try {
			 conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			try { // try the second time, somtime the first attempt fails
				conn = DriverManager.getConnection(url);
			}
			catch (SQLException ie) {
				ie.printStackTrace();
			}
		}
	}
	public ResultSet makeQuery(String cmd, String [] params) {
		try {
			PreparedStatement ps = this.conn.prepareStatement(cmd);
			for (int i=1; i<=params.length;i++) {
				ps.setObject(i, params[i-1]);
			}
			return ps.executeQuery();
		}catch (SQLException e) {
			return null;
		}
	}
}
