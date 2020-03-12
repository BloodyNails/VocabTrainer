package com.bloodynails;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class DBManager {
	
	public static Connection connection = getConnection();
	
	private static Statement s;

	public static Connection getConnection() {
		final String urlPrefix = "jdbc:mysql://localhost:3306/vocabtrainer";
		final String url = urlPrefix +
				"?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
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
				VocabList l = (VocabList) dbObj;
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
						+ w.getID().toString() + "', '" + w.getListID().toString() + "', N'" + w.getWordLang1() + "', N'"
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
				query = "";
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
	
	public static LinkedList<VocabList> getAllLists() {
		
		LinkedList<VocabList> lists = new LinkedList<VocabList>();
		
		final String query = "SELECT * FROM lists;";
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			while(rs.next()) {
				Long id = (Long) rs.getObject("list_id");
				String description = (String) rs.getObject("description");
				String lang1 = (String) rs.getObject("lang1");
				String lang2 = (String) rs.getObject("lang2");
				lists.add(new VocabList(id, description, lang1, lang2));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return lists;
	}

	public static LinkedList<Word> getWordsByListID(Long lID) {
		LinkedList<Word> words = new LinkedList<Word>();
		final String query = "SELECT word_id, word_lang1, word_lang2 FROM vocabtrainer.words WHERE list_id = " + lID + ";";
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			while(rs.next()) {
				Long wID = (Long) rs.getObject("word_id");
				String word1 = (String) rs.getObject("word_lang1");
				String word2 = (String) rs.getObject("word_lang2");
				words.add(new Word(wID, lID, word1, word2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}		
		return words;
	}

	public static VocabList getListByID(Long id) {
		VocabList list = null;
		final String query = "SELECT * FROM lists WHERE list_id = "+id+";";
		
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			while(rs.next()) {
				String description = (String) rs.getObject("description");
				String lang1 = (String) rs.getObject("lang1");
				String lang2 = (String) rs.getObject("lang2");
				list = new VocabList(id, description, lang1, lang2);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
}
