package com.bloodynails;

import java.util.LinkedList;

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
	private float time; // total time which was spent during this round (collect values from the timers inside the cycles)
	private int trueCount; // amount of submitted inputs with true answers (sum of all cycles)
	private int falseCount; // amount of submitted inputs with false answers (sum of all cycles)
	private float tfRatio; // average ration of all cycles
	
	
	public VocabRound(Long ID, VocabPair languages) {
		super(ID, DBObjType.ROUND);
		// TODO
	}
	
	// TODO ...
	
}
