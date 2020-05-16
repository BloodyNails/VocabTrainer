package com.bloodynails;

import com.bloodynails.database.DBManager;
import com.bloodynails.database.DBObj;
import com.bloodynails.database.DBObjType;
import com.bloodynails.database.Savable;

public class VocabWord extends DBObj implements Savable{
	private Long listID;
	private String wordLang1;
	private String wordLang2;
	
	public VocabWord(Long listID, String wordLang1, String wordLang2) {
		super(DBManager.getNextWordID(), DBObjType.WORD);
		
		if(listID == null) throw new NullPointerException("listID must not be null");
		if(listID < 0) throw new IllegalArgumentException("listID must be equal to or greater than 0");
		if(wordLang1 == null) throw new NullPointerException("wordLang1 must not be null");
		if (wordLang1.isEmpty()) throw new IllegalArgumentException("wordLang1 String is empty");
		if(wordLang2 == null) throw new NullPointerException("wordLang2 must not be null");
		if (wordLang2.isEmpty()) throw new IllegalArgumentException("wordLang2 String is empty");
		
		this.listID = listID;
		this.wordLang1 = wordLang1;
		this.wordLang2 = wordLang2;
	}
	
	public VocabWord(Long wordID, Long listID, String wordLang1, String wordLang2) {
		super(wordID, DBObjType.WORD);
		
		if(wordID == null) throw new NullPointerException("wordID must not be null");
		if(wordID < 0) throw new IllegalArgumentException("wordID must be greater or equal to 0");
		if(listID == null) throw new NullPointerException("listID must not be null");
		if(listID < 0) throw new IllegalArgumentException("listID must be equal to or greater than 0");
		if(wordLang1 == null) throw new NullPointerException("wordLang1 must not be null");
		if (wordLang1.isEmpty()) throw new IllegalArgumentException("wordLang1 String is empty");
		if(wordLang2 == null) throw new NullPointerException("wordLang2 must not be null");
		if (wordLang2.isEmpty()) throw new IllegalArgumentException("wordLang2 String is empty");
		
		this.listID = listID;
		this.wordLang1 = wordLang1;
		this.wordLang2 = wordLang2;
	}
	
	@Override
	public String toString() {
		return "VocabWord:"+"\nwordID: "+ID+"\nlistID: "+listID+"\nwordLang1: "+wordLang1+"\nwordLang2: "+wordLang2;
	}
	
	public Long getListID() {
		return this.listID;
	}
	
	public String getWordLang1() {
		return this.wordLang1;
	}
	
	public String getWordLang2() {
		return this.wordLang2;
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
		return DBManager.deleteWordByID(ID);
	}
}
