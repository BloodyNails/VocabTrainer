package com.bloodynails;

import java.util.LinkedList;

public class VocabList extends DBObj{
	private Long ID;
	private String description;
	private String lang1;
	private String lang2;
	private LinkedList<Word> words;
	
	public VocabList (String description, String lang1, String lang2) {
		super(DBManager.getNextListID(), DBObjType.LIST);
		this.ID = DBManager.getNextListID();
		this.description = description;
		this.lang1 = lang1;
		this.lang2 = lang2;
		fillWordsFromDB();
	}
	
	public VocabList(Long listID, String description, String lang1, String lang2) {
		super(listID, DBObjType.LIST);
		this.ID = listID;
		this.description = description;
		this.lang1 = lang1;
		this.lang2 = lang2;
		fillWordsFromDB();
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getLang1() {
		return lang1;
	}

	public String getLang2() {
		return lang2;
	}
	
	// redundant for now
	public boolean addWord(Word word) {
		if (word == null) 
			return false;
		else {
			words.add(word);
			DBManager.save(word);
			return true;
		}
	}
	
	// redundant as addWord has no use 
	public LinkedList<Word> getWords() {
		return words;
	}
	
	public boolean fillWordsFromDB() {
		LinkedList<Word> words = DBManager.getWordsByListID(this.ID);
		if(words.isEmpty() || words == null) {
			words = new LinkedList<Word>();
			return false;
		}
		else {
			this.words = words;
			return true;	
		}
	}

}
