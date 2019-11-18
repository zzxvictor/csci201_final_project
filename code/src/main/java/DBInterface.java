import java.sql.Connection;
import java.sql.*;
import java.sql.SQLException;

import javax.sql.DataSource;
import com.zaxxer.hikari.*;
public class DBInterface {
	private Connection conn ;
	private String connectionName ="nimble-sweep-255322:us-central1:lab8";
	private String password = "zzx74110";
	private String user = "root";
	private String dbName = "lab";
	private String url = "jdbc:mysql://google/lab?cloudSqlInstance=nimble-sweep-255322:us-central1:lab8&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root&password=zzx74110";
	public DBInterface() {
		try {
			Connection conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	public void test() {
		DataSource pool = createConnectionPool();

	}
	
	private DataSource createConnectionPool() {
	    // [START cloud_sql_mysql_servlet_create]
	    // The configuration object specifies behaviors for the connection pool.
	    HikariConfig config = new HikariConfig();

	    // Configure which instance and what database user to connect with.
	    config.setJdbcUrl(String.format("jdbc:mysql:///%s", dbName));
	    config.setUsername(user); // e.g. "root", "postgres"
	    config.setPassword(password); // e.g. "my-password"

	    // For Java users, the Cloud SQL JDBC Socket Factory can provide authenticated connections.
	    // See https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory for details.
	    config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
	    config.addDataSourceProperty("cloudSqlInstance", connectionName);
	    config.addDataSourceProperty("useSSL", "false");

	    // ... Specify additional connection properties here.
	    // [START_EXCLUDE]

	    // [START cloud_sql_mysql_servlet_limit]
	    // maximumPoolSize limits the total number of concurrent connections this pool will keep. Ideal
	    // values for this setting are highly variable on app design, infrastructure, and database.
	    config.setMaximumPoolSize(5);
	    // minimumIdle is the minimum number of idle connections Hikari maintains in the pool.
	    // Additional connections will be established to meet this value unless the pool is full.
	    config.setMinimumIdle(5);
	    // [END cloud_sql_mysql_servlet_limit]

	    // [START cloud_sql_mysql_servlet_timeout]
	    // setConnectionTimeout is the maximum number of milliseconds to wait for a connection checkout.
	    // Any attempt to retrieve a connection from this pool that exceeds the set limit will throw an
	    // SQLException.
	    config.setConnectionTimeout(10000); // 10 seconds
	    // idleTimeout is the maximum amount of time a connection can sit in the pool. Connections that
	    // sit idle for this many milliseconds are retried if minimumIdle is exceeded.
	    config.setIdleTimeout(600000); // 10 minutes
	    // [END cloud_sql_mysql_servlet_timeout]

	    // [START cloud_sql_mysql_servlet_backoff]
	    // Hikari automatically delays between failed connection attempts, eventually reaching a
	    // maximum delay of `connectionTimeout / 2` between attempts.
	    // [END cloud_sql_mysql_servlet_backoff]

	    // [START cloud_sql_mysql_servlet_lifetime]
	    // maxLifetime is the maximum possible lifetime of a connection in the pool. Connections that
	    // live longer than this many milliseconds will be closed and reestablished between uses. This
	    // value should be several minutes shorter than the database's timeout value to avoid unexpected
	    // terminations.
	    config.setMaxLifetime(1800000); // 30 minutes
	    // [END cloud_sql_mysql_servlet_lifetime]

	    // [END_EXCLUDE]

	    // Initialize the connection pool using the configuration object.
	    DataSource pool = new HikariDataSource(config);
	    // [END cloud_sql_mysql_servlet_create]
	    return pool;
	  }*/
}
