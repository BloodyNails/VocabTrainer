package com.bloodynails;

import com.bloodynails.database.DBManager;
import com.bloodynails.database.DBObj;
import com.bloodynails.database.DBObjType;

public class Word extends DBObj{
	private Long listID;
	private String wordLang1;
	private String wordLang2;
	
	public Word(Long listID, String wordLang1, String wordLang2) {
		super(DBManager.getNextWordID(), DBObjType.WORD);
		this.listID = listID;
		this.wordLang1 = wordLang1;
		this.wordLang2 = wordLang2;
	}
	
	public Word(Long wordID, Long listID, String wordLang1, String wordLang2) {
		super(wordID, DBObjType.WORD);
		this.listID = listID;
		this.wordLang1 = wordLang1;
		this.wordLang2 = wordLang2;
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
}
