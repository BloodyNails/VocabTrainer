package com.bloodynails;

import java.util.LinkedList;

import com.bloodynails.database.DBManager;
import com.bloodynails.database.DBObj;
import com.bloodynails.database.DBObjType;

public class VocabRound extends DBObj {
	// TODO: check if languages are compatible with selected lists
	// also make it so only lists of same languages can be selected

	private boolean completed; // was the round completed the last time the user interacted with it
	private LinkedList<Long> listIDs; // IDs of all lists which are selected before creating round
	private LinkedList<Long> cycleIDs; // IDs of the cycles which were
	private VocabPair languages;
	private VocabLang promptedLang;
	private float time; // total time which was spent during this round (collect values from the timers
						// inside the cycles)
	private int trueCount; // amount of submitted inputs with true answers (sum of all cycles)
	private int falseCount; // amount of submitted inputs with false answers (sum of all cycles)
	private float tfRatio; // average ration of all cycles

	public VocabRound(Long ID, boolean completed, LinkedList<Long> listIDs, LinkedList<Long> cycleIDs,
			VocabPair languages, VocabLang promptedLang, float time, int trueCount, int falseCount,
			float tfRatio) {
		super(ID, DBObjType.ROUND);
		this.completed = completed;
		this.listIDs = listIDs;
		this.cycleIDs = cycleIDs;
		this.languages = languages;
		if(languages.contains(promptedLang))
			this.promptedLang = promptedLang;
		this.time = time;
		this.trueCount = trueCount;
		this.falseCount = falseCount;
		this.tfRatio = tfRatio;
	}
	
	public VocabRound(boolean completed, LinkedList<Long> listIDs, LinkedList<Long> cycleIDs,
			VocabPair languages, VocabLang promptedLang, float time, int trueCount, int falseCount,
			float tfRatio) {
		super(DBManager.getNextRoundID(), DBObjType.ROUND);
		this.completed = completed;
		this.listIDs = listIDs;
		this.cycleIDs = cycleIDs;
		this.languages = languages;
		if(languages.contains(promptedLang))
			this.promptedLang = promptedLang;
		this.time = time;
		this.trueCount = trueCount;
		this.falseCount = falseCount;
		this.tfRatio = tfRatio;
	}
	
	@Override
	public String toString() {
		return "roundID: "+ID+"\n"
				+"completed: "+completed+"\n" 
				+"listIDs: "+listIDs.toString()+"\n"
				+"cycleIDs: "+cycleIDs.toString()+"\n"
				+"languages: "+languages.getLang1().toString()+", "+languages.getLang2().toString()+"\n"
				+"promptedLang: "+promptedLang.toString()+"\n"
				+"time: "+time+"\n"
				+"trueCount: "+trueCount+"\n"
				+"falseCount: "+falseCount+"\n"
				+"tfRatio :"+tfRatio;
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
	
	public String listIDsToString() {
		String s = "";
		if(listIDs != null && listIDs.size() > 0) {
			for(int i = 0; i < listIDs.size(); i++) {
				s += listIDs.get(i).toString();
				if(i>0 && i<listIDs.size()-1)
					s += ",";
			}
		}
		return s;
	}
	
	public String cycleIDsToString() {
		String s = "";
		if(cycleIDs != null && cycleIDs.size() > 0) {
			for(int i = 0; i < cycleIDs.size(); i++) {
				s += cycleIDs.get(i).toString();
				if(i>0 && i<cycleIDs.size()-1)
					s += ",";
			}
		}
		return s;
	}

	public LinkedList<Long> getCycleIDs() {
		return cycleIDs;
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

}