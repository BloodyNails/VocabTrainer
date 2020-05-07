package com.bloodynails;

import com.bloodynails.database.DBManager;
import com.bloodynails.database.DBObj;
import com.bloodynails.database.DBObjType;

public class VocabTWord extends DBObj {
	private VocabWord word;
	private Long cycleID;
	private boolean isPrompted;
	private boolean isCorrect;
	
	public VocabTWord(Long twordID, VocabWord word, Long cycleID, boolean isPrompted, boolean isCorrect) {
		super(twordID, DBObjType.WORDTRAINING);
		this.word = word;
		this.cycleID = cycleID;
		this.isPrompted = isPrompted;
		this.isCorrect = isCorrect;
	}
	
	public VocabTWord(VocabWord word, Long cycleID, boolean isPrompted, boolean isCorrect) {
		super(DBManager.getNextTWordID(), DBObjType.WORDTRAINING);
		this.word = word;
		this.cycleID = cycleID;
		this.isPrompted = isPrompted;
		this.isCorrect = isCorrect;
	}

	public VocabWord getWord() {
		return word;
	}

	public Long getCycleID() {
		return cycleID;
	}

	public boolean isPrompted() {
		return isPrompted;
	}
	
	public int isPromptedInt() {
		return isPrompted ? 1 : 0;
	}

	public boolean isCorrect() {
		return isCorrect;
	}
	
	public int isCorrectInt() {
		return isCorrect ? 1 : 0;
	}
	
	@Override
	public String toString() {
		return "VocabTWord:\n" + "tWordID: " + ID + "\nwordID: " + word.getID() + "\ncycleID: " + cycleID
				+ "\nisPrompted: " + isPrompted + "\nisCorrect: " + isCorrect;
	}
}
