package model.persistence;

import java.io.File;
import java.sql.*;

public class DBTransactionManager {
	private static final String dbName = "db" + File.separator + "hit.sqlite";
	private static final String connectionUrl = "jdbc:sqlite:" + dbName;
	
	public DBTransactionManager(){
		final String className = "org.sqlite.JDBC";
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.println("Could not find the database class.");
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		try {
			    // Open a database connection
			Connection connection = DriverManager.getConnection(connectionUrl);
			    
			// Start a transaction
			connection.setAutoCommit(false);
			
			return connection;
		}
		catch (SQLException e) {
		    // ERROR
			System.out.println("Unable to get database connection");
			e.printStackTrace();
			return null;
		}
	}
}
