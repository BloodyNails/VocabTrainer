package com.bloodynails;

import com.bloodynails.database.DBObj;
import com.bloodynails.database.DBObjType;

public class VocabCycle extends DBObj {
	private Long roundID; // ID of the round the cycle belongs to
	private boolean completed; // was the cycle completed the last time the user interacted with it
	private int wordCount; // amount of words which have been asked during this cycle. this value changes based on the answers of previous cycles
	private VocabWord currWord; // word which is currently being asked to the user
	private int trueCount; // correctly answered
	private int falseCount; // incorrectly answered
	private int tfRatio; // tfRatio = falseCount / trueCount
	private VocabTimer timer; // timer for cycle
	
	public VocabCycle(Long ID, Long roundID) {
		super(ID, DBObjType.CYCLE);
		this.roundID = roundID;
		// TODO
	}
	
	public Long getRoundID() {
		return this.roundID;
	}
	
	public boolean setCurrWord(VocabWord currWord) {
		this.currWord = currWord;
		// TODO add proper return statements and save the ID of the word to DB for restoration purposes
		return true;
	}
	
	// TODO ...
}
