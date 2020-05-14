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
		
		if(twordID == null) throw new NullPointerException("tWordID must not be null");
		if(twordID < 0) throw new IllegalArgumentException("tWordID must be equal to or greater than 0");
		if(word == null) throw new NullPointerException("word must not be null");
		if(cycleID == null) throw new NullPointerException("cycleID must not be null");
		if(cycleID < 0) throw new IllegalArgumentException("cycleID must be equal to or greater than 0");
		
		this.word = word;
		this.cycleID = cycleID;
		this.isPrompted = isPrompted;
		this.isCorrect = isCorrect;
	}
	
	public VocabTWord(VocabWord word, Long cycleID, boolean isPrompted, boolean isCorrect) {
		super(DBManager.getNextTWordID(), DBObjType.WORDTRAINING);
		
		if(super.getID() < 0) throw new IllegalArgumentException("tWordID must be equal to or greater than 0");
		if(word == null) throw new NullPointerException("word must not be null");
		if(cycleID == null) throw new NullPointerException("cycleID must not be null");
		if(cycleID < 0) throw new IllegalArgumentException("cycleID must be equal to or greater than 0");
		
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
