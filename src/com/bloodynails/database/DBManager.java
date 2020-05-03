package com.bloodynails.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.bloodynails.VocabLang;
import com.bloodynails.VocabList;
import com.bloodynails.VocabPair;
import com.bloodynails.VocabRound;
import com.bloodynails.VocabWord;
import com.bloodynails.logging.Logger;

public class DBManager {

	public static final Connection connection = getConnection();

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
			Logger.log("Database connection succeeded.");
			return connection;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean save(VocabWord w) {
		if(getWordByID(w.getID()) != null) {
			Logger.log("Updating: " + w.toString());
			return false;
		}
		else {
			Logger.log("Saving: " + w.toString());
			final String query = "INSERT INTO words (word_id, list_id, word_lang1, word_lang2) " + "VALUES ('"
					+ w.getID().toString() + "', '" + w.getListID().toString() + "', N'" + w.getWordLang1()
					+ "', N'" + w.getWordLang2() + "')";
			Logger.log(query);
			return execute(query);
		}
	}
	
	public static boolean save(VocabList l) {
		if(getListByID(l.getID()) != null) {
			Logger.log("Updating: " + l.toString());
			return false;
		}
		else {
			Logger.log("Saving: " + l.toString());
			final String query = "INSERT INTO lists (list_id, description, lang1, lang2) " + "VALUES ('"
					+ l.getID().toString() + "', '" + l.getDescription() + "', '" + l.getLang1().toString()
					+ "', '" + l.getLang2().toString() + "')";
			Logger.log(query);
			return execute(query);
		}
	}
	
	public static boolean save(VocabRound r) {
		if(getRoundByID(r.getID()) != null) {
			Logger.log("Updating: " + r.toString());
			return false;
		}
		else {
			Logger.log("Saving: " + r.toString());
			final String query = "INSERT INTO rounds (round_id, completed, list_ids, cycle_ids, lang1, lang2, prompted_lang, time, true_count, false_count, tf_ratio) "
					+ "VALUES ('" + r.getID() + "', '" + r.isCompletedInt() + "', '" + r.listIDsToString()
					+ "', '" + r.cycleIDsToString() + "', '" + r.getLanguages().getLang1().toString() + "', '"
					+ r.getLanguages().getLang2().toString() + "', '" + r.getPromptedLang().toString() + "', '"
					+ r.getTime() + "', '" + r.getTrueCount() + "', '" + r.getFalseCount() + "', '"
					+ r.getTfRatio() + "')";
			Logger.log(query);
			return execute(query); 
		}
	}
	
	private static boolean execute(String query) {
		try {
			s = connection.createStatement();
			s.execute(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
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
			if (rs.last()) {
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
			if (rs.last()) {
				tmp_id = (Long) rs.getLong("list_id");
			}
			id = tmp_id + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public static Long getNextRoundID() {
		final String query = "SELECT round_id FROM vocabtrainer.rounds ORDER BY round_id ASC;";
		Long id = -1L;
		Long tmp_id = -1L;
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			if (rs.last()) {
				tmp_id = (Long) rs.getLong("round_id");
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
				VocabList l = new VocabList(id, description, VocabPair.parseLangs(lang1, lang2)).fillWordsFromDB();
				lists.add(l);

			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return lists;
	}

	public static LinkedList<VocabRound> getAllRounds() {

		LinkedList<VocabRound> rounds = new LinkedList<VocabRound>();

		final String query = "SELECT * FROM rounds;";
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				Long id = (Long) rs.getLong("round_id");
				boolean completed = rs.getBoolean("completed");
				LinkedList<Long> listIDs = parseIDs(rs.getString("list_ids"));
				LinkedList<Long> cycleIDs = parseIDs(rs.getString("cycle_ids"));
				VocabPair languages = new VocabPair(VocabLang.parseLang(rs.getString("lang1")),
						VocabLang.parseLang(rs.getString("lang2")));
				VocabLang promptedLang = VocabLang.parseLang(rs.getString("prompted_lang"));
				float time = rs.getFloat("time");
				int trueCount = rs.getInt("true_count");
				int falseCount = rs.getInt("false_count");
				float tfRatio = rs.getFloat("tf_ratio");

				VocabRound r = new VocabRound(id, completed, listIDs, cycleIDs, languages, promptedLang, time,
						trueCount, falseCount, tfRatio);
				rounds.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return rounds;
	}

	private static LinkedList<Long> parseIDs(String s) {
		LinkedList<Long> IDs = new LinkedList<Long>();
		if (s != null && !s.isEmpty()) {
			String[] stringIDs = s.split(",");
			for(int i = 0; i < stringIDs.length; i++) {
				if(!IDs.contains(Long.parseLong(stringIDs[i]))) {
					IDs.add(Long.parseLong(stringIDs[i]));
				}
			}
		}
		return IDs;
	}

	public static LinkedList<VocabWord> getWordsByListID(Long listID) {
		if (listID != null && listID > -1) {
			LinkedList<VocabWord> words = new LinkedList<VocabWord>();
			final String query = "SELECT word_id, word_lang1, word_lang2 FROM vocabtrainer.words WHERE list_id = "
					+ listID.toString() + ";";
			try {
				s = connection.createStatement();
				ResultSet rs = s.executeQuery(query);
				while (rs.next()) {
					Long wID = (Long) rs.getLong("word_id");
					String word1 = (String) rs.getString("word_lang1");
					String word2 = (String) rs.getString("word_lang2");
					words.add(new VocabWord(wID, listID, word1, word2));
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
			final String query = "SELECT * FROM lists WHERE list_id = " + listID.toString() + ";";
			try {
				s = connection.createStatement();
				ResultSet rs = s.executeQuery(query);
				if (rs.last()) {
					String description = (String) rs.getString("description");
					String lang1 = (String) rs.getString("lang1");
					String lang2 = (String) rs.getString("lang2");

					return (new VocabList(listID, description, VocabPair.parseLangs(lang1, lang2)).fillWordsFromDB());
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			return null;
		} else {
			return null;
		}
	}
	
	public static VocabWord getWordByID(Long wordID) {
		if (wordID != null && wordID > -1) {
			final String query = "SELECT * FROM words WHERE word_id = " + wordID.toString() + ";";
			try {
				s = connection.createStatement();
				ResultSet rs = s.executeQuery(query);
				if (rs.last()) {
					Long wID = (Long) rs.getLong("word_id");
					String word1 = (String) rs.getString("word_lang1");
					String word2 = (String) rs.getString("word_lang2");
					return new VocabWord(wID, wordID, word1, word2);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			return null;
		} else {
			return null;
		}
	}

	public static VocabRound getRoundByID(Long roundID) {
		if (roundID != null && roundID > -1) {
			final String query = "SELECT * FROM rounds WHERE round_id = " + roundID.toString() + ";";
			try {
				s = connection.createStatement();
				ResultSet rs = s.executeQuery(query);
				if (rs.last()) {
					Long id = (Long) rs.getLong("round_id");
					boolean completed = rs.getBoolean("completed");
					LinkedList<Long> listIDs = parseIDs(rs.getString("list_ids"));
					LinkedList<Long> cycleIDs = parseIDs(rs.getString("cycle_ids"));
					VocabPair languages = new VocabPair(VocabLang.parseLang(rs.getString("lang1")),
							VocabLang.parseLang(rs.getString("lang2")));
					VocabLang promptedLang = VocabLang.parseLang(rs.getString("prompted_lang"));
					float time = rs.getFloat("time");
					int trueCount = rs.getInt("true_count");
					int falseCount = rs.getInt("false_count");
					float tfRatio = rs.getFloat("tf_ratio");

					return (new VocabRound(id, completed, listIDs, cycleIDs, languages, promptedLang, time, trueCount,
							falseCount, tfRatio));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			return null;
		} else {
			return null;
		}
	}

	public static boolean deleteWordByID(Long wordID) {
		final String query = "DELETE FROM words WHERE word_id = " + wordID.toString() + ";";
		final VocabWord word = getWordByID(wordID);
		Logger.log("Deleting WORD: " + word.toString());
		return execute(query);
	}

	public static boolean deleteListByID(Long listID) {
		final String query = "DELETE FROM lists WHERE list_id = " + listID.toString() + ";";
		final VocabList list = getListByID(listID);

		if (list != null) {
			LinkedList<VocabWord> words = list.getWords();
			if (words != null ) {
				if(words.size() > 0) {
					for (int i = 0; i < words.size(); i++) {
						deleteWordByID(words.get(i).getID());
					}
				}
			}
			Logger.log("Deleting List: " + list.toString());
			return execute(query);
		}
		return false;
	}
	
	public static boolean deleteRoundByID(Long roundID) {
		final String query = "DELETE FROM rounds WHERE round_id = " + roundID.toString() + ";";
		final VocabRound round = getRoundByID(roundID);
		Logger.log("Deleting ROUND: " + round.toString());
		return execute(query);
	}

}
