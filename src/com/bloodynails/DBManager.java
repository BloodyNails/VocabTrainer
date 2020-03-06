package com.bloodynails;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

public class DBManager {
	
	public static Connection connection = getConnection();
	
	private static Statement s;

	public static Connection getConnection() {
		final String urlPrefix = "jdbc:mysql://localhost:3306/vocabtrainer";
		final String url = urlPrefix +
				"?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
		final String user = "root";
		final String pass = "pass";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url, user, pass);
			System.out.println("Connection succeeded.");
			return connection;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected static boolean save(DBObj dbObj) {
		String query = "";

		System.out.println("Saving: " + dbObj.getType());

		switch (dbObj.getType()) {
			case LIST:
				List l = (List) dbObj;
				query = "INSERT INTO LISTS (list_id, description, lang1, lang2) " + "VALUES ('" + l.getID().toString()
						+ "', '" + l.getDescription() + "', '" + l.getLang1() + "', '" + l.getLang2() + "')";
				System.out.println(query + "\n");
				try {
					s = connection.createStatement();
					s.execute(query);
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			case WORD:
				Word w = (Word) dbObj;
				query = "INSERT INTO WORDS (word_id, list_id, word_lang1, word_lang2) " + "VALUES ('"
						+ w.getID().toString() + "', '" + w.getListID().toString() + "', '" + w.getWordLang1() + "', '"
						+ w.getWordLang2() + "')";
				System.out.println(query + "\n");
				try {
					s = connection.createStatement();
					s.execute(query);
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			default:
				return false;
		}
	}

	public static Long getNextWordID() {
		final String query = "SELECT word_id FROM vocabtrainer.words ORDER BY word_id ASC;";
		Long id = -1L;
		Long tmp_id = -1L;
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				tmp_id = (Long) rs.getObject("word_id");
			}
			id = tmp_id + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public static Long getNextListID() {
		final String query = "SELECT list_id FROM vocabtrainer.lists ORDER BY list_id ASC;";
		Long id = -1L;
		Long tmp_id = -1L;
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				tmp_id = (Long) rs.getObject("list_id");
			}
			id = tmp_id + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public static LinkedHashMap<String, String> getWordsByListID(Long id) {
		LinkedHashMap<String, String> words = new LinkedHashMap<String, String>();
		//final String query = "SELECT word_lang1, word_lang2 FROM vocabtrainer.words WHERE list_id = " + id + ";";
		
		return words;
	}
}
