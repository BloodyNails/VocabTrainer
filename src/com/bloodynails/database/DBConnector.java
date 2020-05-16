package com.bloodynails.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.bloodynails.logging.Logger;

public class DBConnector {
	
	private String url, user, pass;
	private Connection connection;
	
	public DBConnector(String url, String user, String pass) {
		if(url == null) throw new NullPointerException("url must not be null when creating a DBConnector");
		if(user == null) throw new NullPointerException("user must not be null when creating a DBConnector");
		if(pass == null) throw new NullPointerException("pass must not be null when creating a DBConnector");
		if(url.isEmpty()) throw new IllegalArgumentException("url must not be empty when creating a DBConnector");
		if(user.isEmpty()) throw new IllegalArgumentException("user must not be empty when creating a DBConnector");
		if(pass.isEmpty()) throw new IllegalArgumentException("pass must not be empty when creating a DBConnector");
		
		this.url = url;
		this.user = user;
		this.pass = pass;
	}
	
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, pass);
			Logger.log("Connection established to " + url);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection; 
	}
	
}
