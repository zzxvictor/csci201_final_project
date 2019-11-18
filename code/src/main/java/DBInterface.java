import java.sql.Connection;
import java.sql.*;
import java.sql.SQLException;

import javax.sql.DataSource;
import com.zaxxer.hikari.*;
import com.zaxxer.hikari.HikariConfig;
public class DBInterface {
	private Connection conn ;
	private String url = "jdbc:mysql://google/hw4?cloudSqlInstance=nimble-sweep-255322:us-central1:lab8&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root&password=zzx74110";
	public DBInterface() {
		try {
			 conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.makeQuery("select * from User ", new String [] {});
	}//
	
	public ResultSet makeQuery(String cmd, String [] params) {
		try {
			PreparedStatement ps = this.conn.prepareStatement(cmd);
			for (int i=1; i<params.length;i++) {
				ps.setObject(i, params[i-1]);
			}
			return ps.executeQuery();
		}catch (SQLException e) {
			return null;
		}
	}
}
