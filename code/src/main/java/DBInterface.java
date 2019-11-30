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


/*
 * all DB interaction should be done through this interface for a cleaner code
 * Responsible Developer: Zixuan Zhang 
 */
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
				//System.out.println(e.getMessage());//
			}
		}
	}
	
	/*
	 * make a query (i.e. select * from *)
	 * Parameter: 
	 * 		cmd: a string command like select * from User where userID = ?
	 * 		params: vector, contains parameters that should replace ? in cmd (order matters)
	 * Return:
	 * 		ResultSet: query result 
	 * Responsible Developer: Zixuan Zhang 
	 */
	public ResultSet makeQuery(String cmd, ArrayList params) {
		try {
			PreparedStatement ps = this.conn.prepareStatement(cmd);
			for (int i=0; i<params.size();i++){
				ps.setObject(i+1, params.get(i));
			}
			return ps.executeQuery();
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/*
	 * make a query (i.e. select * from *)
	 * Parameter: 
	 * 		cmd: a string command like insert into User (userID,...) values (?,?,?)
	 * 		params: vector, contains parameters that should replace ? in cmd (order matters)
	 * Return:
	 * 		status: int, signal the query status (success or fail)
	 * Responsible Developer: Zixuan Zhang 
	 */
	
	public int makeUpdate (String cmd, ArrayList params) {
		try {
			PreparedStatement ps = this.conn.prepareStatement(cmd);
			for (int i=0; i<params.size();i++) {
				ps.setObject(i+1, params.get(i));
			}
			return ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
			return -1;//
		}
	}
}
