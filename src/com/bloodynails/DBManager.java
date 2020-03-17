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

	private static Connection getConnection() {
		final String urlPrefix = "jdbc:mysql://localhost:3306/vocabtrainer";
		final String url = urlPrefix
				+ "?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
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

	public static boolean save(DBObj dbObj) {
		String query;

		if (dbObj != null) {
			switch (dbObj.getType()) {
				case LIST:
					VocabList l = (VocabList) dbObj;
					System.out.println("Saving: " + l.getType()+ ": " + l.getDescription());
					query = "INSERT INTO LISTS (list_id, description, lang1, lang2) " + "VALUES ('"
							+ l.getID().toString() + "', '" + l.getDescription() + "', '" + l.getLang1() + "', '"
							+ l.getLang2() + "')";
					break;
				case WORD:
					Word w = (Word) dbObj;
					System.out.println("Saving: " + w.getType()+ ": " + w.getWordLang1() + ", " + w.getWordLang2());
					query = "INSERT INTO WORDS (word_id, list_id, word_lang1, word_lang2) " + "VALUES ('"
							+ w.getID().toString() + "', '" + w.getListID().toString() + "', N'" + w.getWordLang1()
							+ "', N'" + w.getWordLang2() + "')";
					break;
				default:
					query = "";
					break;
			}
			try {
				s = connection.createStatement();
				return s.execute(query);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}

		} else {
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
			if(rs.last()) {
				tmp_id = (Long) rs.getLong("word_id");
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
			if(rs.last()) {
				tmp_id = (Long) rs.getLong("list_id");
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
			while (rs.next()) {
				Long id = (Long) rs.getLong("list_id");
				String description = (String) rs.getString("description");
				String lang1 = (String) rs.getString("lang1");
				String lang2 = (String) rs.getString("lang2");
				lists.add(new VocabList(id, description, lang1, lang2));

			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return lists;
	}

	public static LinkedList<Word> getWordsByListID(Long listID) {
		if (listID != null && listID > -1) {
			LinkedList<Word> words = new LinkedList<Word>();
			final String query = "SELECT word_id, word_lang1, word_lang2 FROM vocabtrainer.words WHERE list_id = "
					+ listID + ";";
			try {
				s = connection.createStatement();
				ResultSet rs = s.executeQuery(query);
				while (rs.next()) {
					Long wID = (Long) rs.getLong("word_id");
					String word1 = (String) rs.getString("word_lang1");
					String word2 = (String) rs.getString("word_lang2");
					words.add(new Word(wID, listID, word1, word2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			return words;
		} else {
			return null;
		}

	}

	public static VocabList getListByID(Long listID) {
		if (listID != null && listID > -1) {
			VocabList list = new VocabList();
			final String query = "SELECT * FROM lists WHERE list_id = " + listID + ";";
			try {
				s = connection.createStatement();
				ResultSet rs = s.executeQuery(query);
				if(rs.last()) {
					String description = (String) rs.getString("description");
					String lang1 = (String) rs.getString("lang1");
					String lang2 = (String) rs.getString("lang2");
					list = new VocabList(listID, description, lang1, lang2);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			return list;
		} else {
			return null;
		}

	}

	public static boolean deleteWordByID(Long wordID) {
		final String query = "DELETE FROM words WHERE word_id = " + wordID;
		try {
			s = connection.createStatement();
			System.out.println("Deleting: WORD: #" + wordID);
			return s.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean deleteListByID(Long listID) {
		final String query = "DELETE FROM lists WHERE list_id = " + listID;
		final VocabList list = getListByID(listID);

		if (list != null) {
			LinkedList<Word> words = list.getWords();
			if (words != null && words.size() > 0) {
				for (int i = 0; i < words.size(); i++) {
					deleteWordByID(words.get(i).getID());
				}
			}
		}

		try {
			s = connection.createStatement();
			System.out.println("Deleting LIST: #" + list.getID() + ", " + list.getDescription());
			return s.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
