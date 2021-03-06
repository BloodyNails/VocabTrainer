package com.bloodynails;

import java.util.LinkedList;

import com.bloodynails.database.DBManager;
import com.bloodynails.database.DBObj;
import com.bloodynails.database.DBObjType;
import com.bloodynails.database.Savable;

public class VocabCycle extends DBObj implements Savable {
	private Long roundID; // ID of the round the cycle belongs to
	private boolean completed; // was the cycle completed the last time the user interacted with it
	private int wordCount; // amount of words which have been asked during this cycle. this value changes based on the answers of previous cycles
	private Long currTWordID; // word which is currently being asked to the user
	private int trueCount; // correctly answered
	private int falseCount; // incorrectly answered
	private float tfRatio; // tfRatio = falseCount / trueCount
	private VocabTimer timer; // timer for cycle
	
	public VocabCycle(Long cycleID, Long roundID, boolean completed, int wordCount, Long currTWordID, int trueCount, int falseCount, float tfRatio, Long time) {
		super(cycleID, DBObjType.CYCLE);

		if(cycleID == null) throw new NullPointerException("cycleID should not be null");
		if(cycleID < 0) throw new IllegalArgumentException("cycleID must be equal to or greater than 0");
		if(wordCount < 0) throw new IllegalArgumentException("a cycle can never have a wordCount less than 0");
		if(currTWordID == null) throw new NullPointerException("currTWordID must not be null");
		if(currTWordID < 0) throw new IllegalArgumentException("currTWordID must be equal to or greater than 0");
		if(trueCount < 0) throw new IllegalArgumentException("a cycle can never have a trueCount less than 0");
		if(falseCount < 0) throw new IllegalArgumentException("a cycle can never have a falseCount less than 0");
		if(tfRatio < 0) throw new IllegalArgumentException("a cycle can never have a tfRatio less than 0");
		if(tfRatio > 1) throw new IllegalArgumentException("a cycle can never have a tfRatio less than 1");
		if(time < 0) throw new IllegalArgumentException("time must be equal to or greater than 0");
			
		this.roundID = roundID;
		this.completed = completed;
		this.wordCount = wordCount;
		this.currTWordID = currTWordID;
		this.trueCount = trueCount;
		this.falseCount = falseCount;
		this.tfRatio = tfRatio;
		this.timer = new VocabTimer(time);
	}
	
	public LinkedList<VocabTWord> getTWords() {
		return DBManager.getTWordsByCycleID(this.ID);
	}
	
	public Long getRoundID() {
		return this.roundID;
	}
	
	public void setCurrTWordID(Long currTWordID) {
		this.currTWordID = currTWordID;
	}

	public boolean isCompleted() {
		return completed;
	}
	
	public int isCompletedInt() {
		return completed ? 1 : 0; 
	}

	public int getWordCount() {
		return wordCount;
	}

	public Long getCurrTWordID() {
		return currTWordID;
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

	public VocabTimer getTimer() {
		return timer;
	}
	
	@Override
	public String toString() {
		return "cycleID: " + ID + "\nroundID: " + roundID + "\ncompleted: " + completed + "\nwordCount: " + wordCount
				+ "\ncurrWordID: " + currTWordID + "\ntrueCount: " + trueCount + "\nfalseCount: " + falseCount
				+ "\ntfRatio: " + tfRatio + "\ntime: " + timer.getCurrTime();
	}
	
	// Interface Mthods
	@Override
	public boolean save() {
		return DBManager.save(this);
	}
	
	@Override
	public Boolean isSaved() {
		return DBManager.isSaved(type, ID);
	}
	
	@Override
	public boolean delete() {
		return DBManager.deleteCycleByID(ID);
	}
}
