package com.bloodynails;

import java.util.LinkedList;

import com.bloodynails.database.DBManager;
import com.bloodynails.database.DBObj;
import com.bloodynails.database.DBObjType;
import com.bloodynails.logging.Logger;
import com.bloodynails.logging.MessageType;

public class VocabRound extends DBObj {
	// TODO: make it so only lists of same languages can be selected

	private boolean completed; // was the round completed the last time the user interacted with it
	private LinkedList<Long> listIDs; // IDs of all lists which are selected before creating round
	private LinkedList<Long> cycleIDs; // IDs of the cycles which were
	private VocabPair languages;
	private VocabLang promptedLang;
	private float time; // total time which was spent during this round (collect values from the timers inside the cycles)
	private int trueCount; // amount of submitted inputs with true answers (sum of all cycles)
	private int falseCount; // amount of submitted inputs with false answers (sum of all cycles)
	private float tfRatio; // average ration of all cycles

	public VocabRound(Long roundID, boolean completed, LinkedList<Long> listIDs, LinkedList<Long> cycleIDs,
			VocabPair languages, VocabLang promptedLang, float time, int trueCount, int falseCount, float tfRatio) {

		super(roundID, DBObjType.ROUND);

		if (roundID < 0) throw new IllegalArgumentException("roundID must be greater or equal to 0");
		if (listIDs == null) throw new NullPointerException("listIDs must not be null");
		if (cycleIDs == null) throw new NullPointerException("cycleIDs must not be null");
		if (languages == null) throw new NullPointerException("languages must not be null");
		if (promptedLang == null) throw new NullPointerException("promptedLang must not be null");
		if (!languages.contains(promptedLang))
			throw new IllegalArgumentException("promptedLang must be contained by languages");
		if (time < 0) time = 0;
		if (trueCount < 0) trueCount = 0;
		if (falseCount < 0) falseCount = 0;
		if (tfRatio < 0) tfRatio = 0;
		if (tfRatio > 1) tfRatio = 1;

		this.completed = completed;
		this.listIDs = listIDs;
		this.cycleIDs = cycleIDs;
		this.languages = languages;
		this.promptedLang = promptedLang;
		this.time = time;
		this.trueCount = trueCount;
		this.falseCount = falseCount;
		this.tfRatio = tfRatio;
		removeWrongLangs();
	}

	public VocabRound(boolean completed, LinkedList<Long> listIDs, LinkedList<Long> cycleIDs, VocabPair languages,
			VocabLang promptedLang, float time, int trueCount, int falseCount, float tfRatio) {

		super(DBManager.getNextRoundID(), DBObjType.ROUND);

		if (listIDs == null) throw new NullPointerException("listIDs must not be null");
		if (cycleIDs == null) throw new NullPointerException("cycleIDs must not be null");
		if (languages == null) throw new NullPointerException("languages must not be null");
		if (promptedLang == null) throw new NullPointerException("promptedLang must not be null");
		if (!languages.contains(promptedLang))
			throw new IllegalArgumentException("promptedLang must be contained by languages");
		if (time < 0) time = 0;
		if (trueCount < 0) trueCount = 0;
		if (falseCount < 0) falseCount = 0;
		if (tfRatio < 0) tfRatio = 0;
		if (tfRatio > 1) tfRatio = 1;

		this.completed = completed;
		this.listIDs = listIDs;
		this.cycleIDs = cycleIDs;
		this.languages = languages;
		if (languages.contains(promptedLang)) this.promptedLang = promptedLang;
		this.time = time;
		this.trueCount = trueCount;
		this.falseCount = falseCount;
		this.tfRatio = tfRatio;
		removeWrongLangs();
	}

	@Override
	public String toString() {
		return "roundID: " + ID + "\n" + "completed: " + completed + "\n" + "listIDs: " + listIDs.toString() + "\n"
				+ "cycleIDs: " + cycleIDs.toString() + "\n" + "languages: " + languages.getLang1().toString() + ", "
				+ languages.getLang2().toString() + "\n" + "promptedLang: " + promptedLang.toString() + "\n" + "time: "
				+ time + "\n" + "trueCount: " + trueCount + "\n" + "falseCount: " + falseCount + "\n" + "tfRatio: "
				+ tfRatio;
	}

	public VocabRound removeWrongLangs() {
		if(listIDs == null) return null;
		if(listIDs.size() < 1) return null;
		
		for (int i = 0; i < listIDs.size(); i++) {
			VocabList list = DBManager.getListByID(listIDs.get(i));
			if(list == null) {
				Logger.log(MessageType.WARNING, "List specified inside round is not found DB");
				return null;
			}
			
			VocabPair listLangs = list.getLangs();
			if (!listLangs.compareTo(languages)) {
				listIDs.remove(i);
			}
		}
		
		if(listIDs.size() < 1) {
			Logger.log(MessageType.WARNING, "no more lists in round #" + super.getID() + " after deleting the wrong langauges");
		}
		
		return this;
	}

	public boolean isCompleted() {
		return completed;
	}

	public int isCompletedInt() {
		return completed ? 1 : 0;
	}

	public LinkedList<Long> getListIDs() {
		return listIDs;
	}

	public LinkedList<VocabList> getLists() {
		if (listIDs == null) return null;
		if (listIDs.size() < 1) return new LinkedList<VocabList>();
		LinkedList<VocabList> lists = new LinkedList<VocabList>();
		for (int i = 0; i < this.listIDs.size(); i++) {
			VocabList l = DBManager.getListByID(this.listIDs.get(i));
			lists.add(l);
		}
		return lists;
	}

	public String listIDsToString() {
		if (listIDs == null) return null;
		if (listIDs.size() < 1) return new String();
		String s = "";
		for (int i = 0; i < listIDs.size(); i++) {
			if (i > 0) s += ",";
			s += listIDs.get(i).toString();
		}
		return s;
	}

	public String[] getListDescriptions() {
		if (listIDs == null) return null;
		if (listIDs.size() < 1) return new String[] {"no lists"};
		
		String[] descriptions = new String[listIDs.size()];
		for (int i = 0; i < listIDs.size(); i++) {
			if (DBManager.getListByID(listIDs.get(i)) == null) {
				Logger.log(MessageType.WARNING, "List specified inside round is not found DB");
				descriptions[i] = "List #" + listIDs.get(i).toString() + " not found";
			} else {
				descriptions[i] = DBManager.getListByID(listIDs.get(i)).getDescription();
			}
		}
		return descriptions;
	}

	public String cycleIDsToString() {
		if (cycleIDs == null) return null;
		if (cycleIDs.size() < 1) return new String();
		String s = new String();
		for (int i = 0; i < cycleIDs.size(); i++) {
			if (i > 0) s += ",";
			s += cycleIDs.get(i).toString();
		}
		return s;
	}

	public LinkedList<Long> getCycleIDs() {
		return cycleIDs;
	}

	public LinkedList<VocabCycle> getCycles() {
		return DBManager.getCyclesByRoundID(this.ID);
	}

	public VocabPair getLanguages() {
		return languages;
	}

	public VocabLang getPromptedLang() {
		return promptedLang;
	}

	public float getTime() {
		return time;
	}

	public int getTrueCount() {
		return trueCount;
	}

	public int getFalseCount() {
		return falseCount;
	}

	public float getTfRatio() {
		return tfRatio;
	}

	public void addTime(float time) {
		this.time += time;
	}

	public void addCycleID(Long cycleID) {
		cycleIDs.add(cycleID);
	}

}
