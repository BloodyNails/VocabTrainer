package com.bloodynails;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
	
	public static Connection getConnection() {
		String urlPrefix = "jdbc:mysql://localhost:3306/vocabtrainer";
		String url = urlPrefix + "?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user = "root";
		String pass = "pass";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url, user, pass);
			return connection;
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	protected static boolean save(DBObj dbObj) {	
		String query = "";
		Connection c = getConnection();
		Statement s;

		System.out.println(dbObj.getType());
		
		switch(dbObj.getType()) {
		case LIST:
			System.out.println("saving list to db");
			
			List l = (List)dbObj;
			query = "INSERT INTO LISTS (list_id, description, lang1, lang2) " +
					"VALUES ('" + l.getID().toString() + "', '" + l.getDescription() + "', '" + l.getLang1() + "', '" + l.getLang2() + "')";
			System.out.println(query + "\n");
			try {
				s = c.createStatement();
				s.execute(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		case WORD:
			System.out.println("saving word to db");
			Word w = (Word)dbObj;
			query = "INSERT INTO WORDS (word_id, list_id, word_lang1, word_lang2) " +
					"VALUES ('" + w.getID().toString() + "', '" + w.getListID().toString() + "', '" + w.getWordLang1() + "', '" + w.getWordLang2() + "')";
			System.out.println(query + "\n");
			try {
				s = c.createStatement();
				s.execute(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public static Long getNextWordID() {
		String query = "SELECT word_id FROM vocabtrainer.words ORDER BY word_id ASC;";
		Long id = -1L;
		Long tmp_id = -1L;
		Connection c = getConnection();
		Statement s;
		try {
			s = c.createStatement();
			ResultSet rs = s.executeQuery(query);
			while(rs.next()) {
				tmp_id = (Long) rs.getObject("word_id");
				System.out.println(tmp_id);
			}
			id = tmp_id+1;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public static Long getNextListID() {
		String query = "SELECT list_id FROM vocabtrainer.lists ORDER BY list_id ASC;";
		Long id = -1L;
		Long tmp_id = -1L;
		Connection c = getConnection();
		Statement s;
		try {
			s = c.createStatement();
			ResultSet rs = s.executeQuery(query);
			while(rs.next()) {
				tmp_id = (Long) rs.getObject("list_id");
			}
			id = tmp_id+1;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
}
