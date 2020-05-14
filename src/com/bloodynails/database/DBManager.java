package com.bloodynails.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.bloodynails.Config;
import com.bloodynails.VocabCycle;
import com.bloodynails.VocabLang;
import com.bloodynails.VocabList;
import com.bloodynails.VocabPair;
import com.bloodynails.VocabRound;
import com.bloodynails.VocabWord;
import com.bloodynails.VocabTWord;
import com.bloodynails.logging.Logger;
import com.bloodynails.logging.MessageType;


// TODO: getNext...ID should return the lowest possible ID
public class DBManager {

	public static final Connection connection = getConnection();

	private static Statement s;

	private static Connection getConnection() {
		final String url = Config.dbUrl;
		final String user = Config.dbUser;
		final String pass = Config.dbPass;
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
		if (w == null) return false;
		if (isSavedVocabWord(w.getID())) {
			Logger.log("Updating VocabWord not implemented yet");
			// TODO implement update(VocabWord)
			return false;
		} else {
			final String query = "INSERT INTO " + Config.dbTableWords + " (word_id, list_id, word_lang1, word_lang2) "
					+ "VALUES ('" + w.getID().toString() + "', '" + w.getListID().toString() + "', N'"
					+ w.getWordLang1() + "', N'" + w.getWordLang2() + "');";
			Logger.log(query);
			return execute(query);
		}
	}

	public static boolean isSavedVocabWord(Long wordID) {
		return (getWordByID(wordID) == null) ? false : true;
	}

	public static boolean save(VocabTWord wt) {
		if (isSavedVocabTWord(wt)) {
			Logger.log(MessageType.WARNING, "Updating VocabWordTraining not implemented yet");
			// TODO implement update(VocabTWord)
			return false;
		} else {
			final String query = "INSERT INTO " + Config.dbTableTWords
					+ " (tword_id, word_id, cycle_id, is_prompted, is_correct) " + "VALUES ('" + wt.getID().toString()
					+ "', '" + wt.getWord().getID() + "', '" + wt.getCycleID().toString() + "', '" + wt.isPromptedInt()
					+ "', '" + wt.isCorrectInt() + "');";
			Logger.log(query);
			return execute(query);
		}
	}

	public static boolean isSavedVocabTWord(VocabTWord wt) {
		return (getTWordByID(wt.getID()) == null) ? false : true;
	}

	public static boolean save(VocabList l) {
		if (isSavedVocabList(l)) {
			Logger.log(MessageType.WARNING, "Updating VocabList not implemented yet");
			// TODO implement update(VocabList)
			return false;
		} else {
			;
			final String query = "INSERT INTO " + Config.dbTableLists + " (list_id, description, lang1, lang2) "
					+ "VALUES ('" + l.getID().toString() + "', N'" + l.getDescription() + "', '"
					+ l.getLang1().toString() + "', '" + l.getLang2().toString() + "');";
			Logger.log(query);
			return execute(query);
		}
	}

	public static boolean isSavedVocabList(VocabList l) {
		return (getListByID(l.getID()) == null) ? false : true;
	}

	public static boolean save(VocabRound r) {
		if (isSavedVocabRound(r)) {
			Logger.log(MessageType.WARNING, "Updating VocabRound not implemented yet");
			// TODO implement update(VocabRound)
			return false;
		} else {
			final String query = "INSERT INTO " + Config.dbTableRounds
					+ " (round_id, completed, list_ids, lang1, lang2, prompted_lang, time, true_count, false_count, tf_ratio) "
					+ "VALUES ('" + r.getID() + "', '" + r.isCompletedInt() + "', '" + r.listIDsToString() + "', '"
					+ r.getLanguages().getLang1().toString() + "', '" + r.getLanguages().getLang2().toString() + "', '"
					+ r.getPromptedLang().toString() + "', '" + r.getTime() + "', '" + r.getTrueCount() + "', '"
					+ r.getFalseCount() + "', '" + r.getTfRatio() + "');";
			Logger.log(query);
			return execute(query);
		}
	}
	
	public static boolean isSavedVocabRound(VocabRound r) {
		return (getRoundByID(r.getID()) == null) ? false : true;
	}

	public static boolean save(VocabCycle c) {
		if (isSavedVocabCycle(c)) {
			Logger.log("Updating VocabCycle not implemented yet");
			return false;
		} else {
			final String query = "INSERT INTO " + Config.dbTableCycles
					+ " (cycle_id, round_id, completed, word_count, curr_tword_id, true_count, false_count, tf_ratio, time)"
					+ " VALUES ('" + c.getID() + "', '" + c.getRoundID() + "', '" + c.isCompletedInt() + "', '"
					+ c.getWordCount() + "', '" + c.getCurrTWordID() + "', '" + c.getTrueCount() + "', '"
					+ c.getFalseCount() + "', '" + c.getTfRatio() + "', '" + c.getTimer().getCurrTime() + "');";
			Logger.log(query);
			return execute(query);
		}
	}

	public static boolean isSavedVocabCycle(VocabCycle c) {
		return (getCycleByID(c.getID()) == null) ? false : true;
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
		final String query = "SELECT word_id FROM " + Config.dbTableWords + " ORDER BY word_id ASC;";
		Long id = -1L;
		Long tmp_id = -1L;
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			if (rs.last()) {
				tmp_id = rs.getLong("word_id");
			}
			id = tmp_id + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public static Long getNextTWordID() {
		final String query = "SELECT tword_id FROM " + Config.dbTableTWords + " ORDER BY word_id ASC;";
		Long id = -1L;
		Long tmp_id = -1L;
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			if (rs.last()) {
				tmp_id = rs.getLong("tword_id");
			}
			id = tmp_id + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public static Long getNextListID() {
		final String query = "SELECT list_id FROM " + Config.dbTableLists + " ORDER BY list_id ASC;";
		Long id = -1L;
		Long tmp_id = -1L;
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			if (rs.last()) {
				tmp_id = rs.getLong("list_id");
			}
			id = tmp_id + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public static Long getNextRoundID() {
		final String query = "SELECT round_id FROM " + Config.dbTableRounds + " ORDER BY round_id ASC;";
		Long id = -1L;
		Long tmp_id = -1L;
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			if (rs.last()) {
				tmp_id = rs.getLong("round_id");
			}
			id = tmp_id + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public static Long getNextCycleID() {
		final String query = "SELECT cycle_id FROM " + Config.dbTableCycles + " ORDER BY cycle_id ASC;";
		Long id = -1L;
		Long tmp_id = -1L;
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			if (rs.last()) {
				tmp_id = rs.getLong("cycle_id");
			}
			id = tmp_id + 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public static LinkedList<VocabList> getAllLists() {
		LinkedList<VocabList> lists = new LinkedList<VocabList>();

		final String query = "SELECT * FROM " + Config.dbTableLists + ";";
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				Long id = rs.getLong("list_id");
				String description = rs.getString("description");
				String lang1 = rs.getString("lang1");
				String lang2 = rs.getString("lang2");
				VocabList l = new VocabList(id, description, VocabPair.parseLangs(lang1, lang2));
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

		final String query = "SELECT * FROM " + Config.dbTableRounds + ";";
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				Long roundID = rs.getLong("round_id");
				boolean completed = rs.getBoolean("completed");
				String listIDsStr = rs.getString("list_ids");
				String lang1 = rs.getString("lang1");
				String lang2 = rs.getString("lang2");
				String pLang = rs.getString("prompted_lang");
				float time = rs.getFloat("time");
				int trueCount = rs.getInt("true_count");
				int falseCount = rs.getInt("false_count");
				float tfRatio = rs.getFloat("tf_ratio");

				LinkedList<Long> listIDs = parseIDs(listIDsStr);
				LinkedList<Long> cycleIDs = new LinkedList<Long>();
				LinkedList<VocabCycle> cycles = getCyclesByRoundID(roundID);
				if (cycles != null) {
					for (int i = 0; i < cycles.size(); i++) {
						VocabCycle c = cycles.get(i);
						cycleIDs.add(c.getID());
					}
				}

				VocabPair languages = new VocabPair(VocabLang.parseLang(lang1), VocabLang.parseLang(lang2));
				VocabLang promptedLang = VocabLang.parseLang(pLang);

				rounds.add(new VocabRound(roundID, completed, listIDs, cycleIDs, languages, promptedLang, time,
						trueCount, falseCount, tfRatio));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return rounds;
	}

	private static LinkedList<Long> parseIDs(String s) {
		LinkedList<Long> IDs = new LinkedList<Long>();
		if(s == null) return IDs;
		if(s.isEmpty()) return IDs;
		
		String[] stringIDs = s.split(",");
		for (int i = 0; i < stringIDs.length; i++) {
			Long id = Long.parseLong(stringIDs[i]);
			if (!IDs.contains(id)) {
				IDs.add(id);
			}
		}
		
		return IDs;
	}

	public static LinkedList<VocabWord> getWordsByListID(Long listID) {
		if(listID == null || listID < 0) return null;
		
		LinkedList<VocabWord> words = new LinkedList<VocabWord>();
		final String query = "SELECT * FROM " + Config.dbTableWords + " WHERE list_id = " + listID.toString() + ";";
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				Long wID = rs.getLong("word_id");
				String word1 = rs.getString("word_lang1");
				String word2 = rs.getString("word_lang2");
				words.add(new VocabWord(wID, listID, word1, word2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return words;
	}

	public static LinkedList<VocabList> getListsByRoundID(Long roundID) {
		return null;
	}

	public static LinkedList<VocabTWord> getTWordsByWordID(Long wordID) {
		if(wordID == null || wordID < 0) return null;
		
		LinkedList<VocabTWord> tWords = new LinkedList<VocabTWord>();
		final String query = "SELECT * FROM " + Config.dbTableTWords + " WHERE word_id = " + wordID.toString()
				+ ";";
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				Long twID = rs.getLong("tword_id");
				Long cycleID = rs.getLong("cycle_id");
				boolean prompted = rs.getBoolean("is_prompted");
				boolean correct = rs.getBoolean("is_correct");
				VocabWord w = getWordByID(wordID);
				VocabTWord tw = new VocabTWord(twID, w, cycleID, prompted, correct);
				tWords.add(tw);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return tWords;
	}

	public static LinkedList<VocabTWord> getTWordsByCycleID(Long cycleID) {
		if(cycleID == null || cycleID < 0) return null;
		
		LinkedList<VocabTWord> tWords = new LinkedList<VocabTWord>();
		final String query = "SELECT * FROM " + Config.dbTableTWords + " WHERE cycle_id = " + cycleID.toString()
				+ ";";
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				Long tWordID = rs.getLong("tword_id");
				Long wordID = rs.getLong("word_id");
				boolean prompted = rs.getBoolean("is_prompted");
				boolean correct = rs.getBoolean("is_correct");
				VocabWord w = getWordByID(wordID);
				VocabTWord tw = new VocabTWord(tWordID, w, cycleID, prompted, correct);
				tWords.add(tw);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return tWords;
	}

	public static LinkedList<VocabCycle> getCyclesByRoundID(Long roundID) {
		if(roundID == null || roundID < 0) return null;
		
		LinkedList<VocabCycle> cycles = new LinkedList<VocabCycle>();
		final String query = "SELECT * FROM " + Config.dbTableCycles + " WHERE round_id = " + roundID.toString()
				+ ";";
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			while (rs.next()) {
				Long cycleID = rs.getLong("cycle_id");
				boolean completed = rs.getBoolean("completed");
				int wordCount = rs.getInt("word_count");
				Long currTWordID = rs.getLong("curr_tword_id");
				int trueCount = rs.getInt("true_count");
				int falseCount = rs.getInt("false_count");
				float tfRatio = rs.getFloat("tf_ratio");
				float time = rs.getFloat("time");
				VocabCycle c = new VocabCycle(cycleID, roundID, completed, wordCount, currTWordID, trueCount,
						falseCount, tfRatio, time);
				cycles.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return cycles;
	}

	public static VocabList getListByID(Long listID) {
		if(listID == null || listID < 0) return null;
		
		final String query = "SELECT * FROM " + Config.dbTableLists + " WHERE list_id = " + listID.toString() + ";";
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			
			if(!rs.last()) return null;
			
			String description = rs.getString("description");
			String lang1 = rs.getString("lang1");
			String lang2 = rs.getString("lang2");

			return (new VocabList(listID, description, VocabPair.parseLangs(lang1, lang2)));
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static VocabWord getWordByID(Long wordID) {
		if (wordID == null || wordID < 0) return null;
		
		final String query = "SELECT * FROM " + Config.dbTableWords + " WHERE word_id = " + wordID.toString() + ";";
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			if (rs.last()) {
				String word1 = rs.getString("word_lang1");
				String word2 = rs.getString("word_lang2");
				return new VocabWord(wordID, wordID, word1, word2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public static VocabTWord getTWordByID(Long tWordID) {
		if (tWordID == null || tWordID < 0) return null;
		
		final String query = "SELECT * FROM " + Config.dbTableTWords + " WHERE tword_id = " + tWordID.toString()
				+ ";";
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			if (rs.last()) {
				Long wID = rs.getLong("word_id");
				Long cID = rs.getLong("cycle_id");
				boolean prompted = rs.getBoolean("is_prompted");
				boolean correct = rs.getBoolean("is_correct");
				VocabWord word = getWordByID(wID);
				if (word == null) return null;
				return new VocabTWord(word, cID, prompted, correct);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public static VocabRound getRoundByID(Long roundID) {
		if (roundID == null || roundID < 0) return null;
		
		final String query = "SELECT * FROM " + Config.dbTableRounds + " WHERE round_id = " + roundID.toString() + ";";

		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			if (!rs.last()) return null;

			boolean completed = rs.getBoolean("completed");
			String listIDsStr = rs.getString("list_ids");
			String lang1 = rs.getString("lang1");
			String lang2 = rs.getString("lang2");
			String pLang = rs.getString("prompted_lang");
			float time = rs.getFloat("time");
			int trueCount = rs.getInt("true_count");
			int falseCount = rs.getInt("false_count");
			float tfRatio = rs.getFloat("tf_ratio");

			LinkedList<Long> listIDs = parseIDs(listIDsStr);
			LinkedList<Long> cycleIDs = new LinkedList<Long>();
			LinkedList<VocabCycle> cycles = getCyclesByRoundID(roundID);
			if(cycles == null) return null;
			
			for (int i = 0; i < cycles.size(); i++) {
				VocabCycle c = cycles.get(i);
				cycleIDs.add(c.getID());
			}

			VocabPair languages = new VocabPair(VocabLang.parseLang(lang1), VocabLang.parseLang(lang2));
			VocabLang promptedLang = VocabLang.parseLang(pLang);

			return (new VocabRound(roundID, completed, listIDs, cycleIDs, languages, promptedLang, time, trueCount,
					falseCount, tfRatio));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static VocabCycle getCycleByID(Long cycleID) {
		if (cycleID == null || cycleID < 0) return null;
		
		final String query = "SELECT * FROM " + Config.dbTableCycles + " WHERE cycle_id = " + cycleID.toString() + ";";
		try {
			s = connection.createStatement();
			ResultSet rs = s.executeQuery(query);
			if (!rs.last()) return null;

			Long roundID = rs.getLong("round_id");
			boolean completed = rs.getBoolean("completed");
			int wordCount = rs.getInt("word_count");
			Long currTWordID = rs.getLong("curr_tword_id");
			int trueCount = rs.getInt("true_count");
			int falseCount = rs.getInt("false_count");
			float tfRatio = rs.getFloat("tf_ratio");
			float time = rs.getFloat("time");
			
			return new VocabCycle(cycleID, roundID, completed, wordCount, currTWordID, trueCount, falseCount, tfRatio,
					time);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static boolean deleteListByID(Long listID) {
		if(listID == null || listID < 0) return false;
		final String query = "DELETE FROM " + Config.dbTableLists + " WHERE list_id = " + listID.toString() + ";";
		final VocabList list = getListByID(listID);
		
		LinkedList<VocabWord> words = list.getWords();
		if (words == null) return false;
		for (int i = 0; i < words.size(); i++) {
			if (!deleteWordByID(words.get(i).getID())) {
				Logger.log(MessageType.WARNING,
						"Could not delete word #" + words.get(i).getID() + " of list #" + listID);
			}
		}
		
		Logger.log(query);
		return execute(query);
	}

	public static boolean deleteWordByID(Long wordID) {
		if(wordID == null || wordID < 0) return false;
		if(!isSavedVocabWord(wordID)) return false;
		
		final String query = "DELETE FROM " + Config.dbTableWords + " WHERE word_id = " + wordID.toString() + ";";
		
		LinkedList<VocabTWord> tWords = getTWordsByWordID(wordID);
		if(tWords == null) {
			Logger.log("getTWordsByWordID("+wordID+") returned null");
			return false;
		}
		
		for (int i = 0; i < tWords.size(); i++) {
			VocabTWord tWord = tWords.get(i);
			if (tWord != null) {
				if (!deleteTWordByID(tWord.getID())) {
					Logger.log(MessageType.WARNING,
							"Could not delete tWord #" + tWord.getID() + " which uses word #" + wordID);
				}
			}
		}
		
		Logger.log(query);
		return execute(query);
	}

	public static boolean deleteTWordByID(Long tWordID) {
		if(tWordID == null || tWordID < 0) return false;
		
		final String query = "DELETE FROM " + Config.dbTableTWords + " WHERE tword_id = " + tWordID.toString() + ";";
		Logger.log(query);
		return execute(query);
	}

	public static boolean deleteRoundByID(Long roundID) {
		if(roundID == null || roundID < 0) return false;
		
		final String query = "DELETE FROM " + Config.dbTableRounds + " WHERE round_id = " + roundID.toString() + ";";
		final VocabRound round = getRoundByID(roundID);

		if(round == null) {
			Logger.log(MessageType.WARNING, "round #" + roundID + " could not be found");
			return false;
		}
		
		LinkedList<Long> cycleIDs = round.getCycleIDs();
		if(cycleIDs == null) {
			Logger.log(MessageType.WARNING, "cycleIDs of round #" + roundID + " is null");
			return false;
		}
		
		if (cycleIDs.size() > 0) {
			LinkedList<VocabCycle> cycles = round.getCycles();
			if(cycles == null) {
				Logger.log(MessageType.WARNING, "getCycles() of round #" + roundID + " is null");
				return false;
			}
			
			for (int i = 0; i < cycles.size(); i++) {
				if (!deleteCycleByID(cycles.get(i).getID())) {
					Logger.log(MessageType.WARNING,
							"Could not delete cycle #" + cycles.get(i).getID() + " of round #" + roundID);
					return false;
				}
			}
		}
		
		Logger.log(query);
		return execute(query);
	}

	public static boolean deleteCycleByID(Long cycleID) {
		if (cycleID == null || cycleID < 0) return false;
		
		final String query = "DELETE FROM " + Config.dbTableCycles + " WHERE cycle_id = " + cycleID.toString() + ";";
		final VocabCycle cycle = getCycleByID(cycleID);
		if (cycle == null) return false;
		LinkedList<VocabTWord> tWords = cycle.getTWords();
		if (tWords != null) {
			for (int i = 0; i < tWords.size(); i++) {
				if (!deleteTWordByID(tWords.get(i).getID())) {
					Logger.log(MessageType.WARNING,
							"Could not delete tWord #" + tWords.get(i).getID() + " of cycle #" + cycleID);
				}
			}
		}
		
		Logger.log(query);
		return execute(query);
	}

}
