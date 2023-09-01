package projects.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import projects.exception.DbException;

public class DbConnection {
	private static String HOST = "localhost";
	private static String PASSWORD = "Promineo$56";
	private static int PORT = 3306;
	private static String SCHEMA = "projects";
	private static String USER = "projects";
	
	public static java.sql.Connection getConnection(){
		String uri = String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s&useSSL=false",
				HOST, PORT, SCHEMA, USER, PASSWORD);
		
		try {
			Connection conn = DriverManager.getConnection(uri);
			return conn;
		} catch (SQLException e) {
			System.out.println("Error getting connection.");
			throw new DbException(e);
		}
	}
}
