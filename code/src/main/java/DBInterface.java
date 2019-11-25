import java.sql.Connection;
import java.io.Serializable;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.sql.DataSource;

import org.json.simple.JSONObject;

import com.zaxxer.hikari.*;
import com.zaxxer.hikari.HikariConfig;
public class DBInterface implements Serializable{
	private Connection conn ;
	private String url = "jdbc:mysql://google/FinalProject?cloudSqlInstance=finalproject-258505:us-central1:final-project-db&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root&password=201FinalProjectRoster&autoReconnect=true";
	private static final long serialVersionUID = 1l;
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
	public ResultSet makeQuery(String cmd, ArrayList params) {
		try {
			PreparedStatement ps = this.conn.prepareStatement(cmd);
			for (int i=0; i<params.size();i++){
				ps.setObject(i+1, params.get(i));
			}
			return ps.executeQuery();
		}catch (SQLException e) {
			return null;
		}
	}
	
	public int makeUpdate (String cmd, ArrayList params) {
		try {
			PreparedStatement ps = this.conn.prepareStatement(cmd);
			for (int i=0; i<params.size();i++) {
				ps.setObject(i+1, params.get(i));
			}
			return ps.executeUpdate();
		}catch (SQLException e) {
			return -1;
		}
	}
}
