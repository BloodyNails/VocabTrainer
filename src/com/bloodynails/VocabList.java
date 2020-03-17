package com.bloodynails;

import java.util.LinkedList;

public class VocabList extends DBObj{
	private String description;
	private String lang1;
	private String lang2;
	private LinkedList<Word> words;
	
	public VocabList() {
		// only needed when a VocabList must be initialized!
		super(-1L, DBObjType.LIST);
		this.description = "";
		this.lang1 = "";
		this.lang2 = "";
		words = new LinkedList<Word>();
	}
	
	public VocabList (String description, String lang1, String lang2) {
		super(DBManager.getNextListID(), DBObjType.LIST);
		this.description = description;
		this.lang1 = lang1;
		this.lang2 = lang2;
		words = new LinkedList<Word>();
		fillWordsFromDB();
	}
	
	public VocabList(Long listID, String description, String lang1, String lang2) {
		super(listID, DBObjType.LIST);
		this.description = description;
		this.lang1 = lang1;
		this.lang2 = lang2;
		words = new LinkedList<Word>();
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
	
	public boolean addWord(Word word) {
		if (word != null && words != null) {
			words.add(word);
			return DBManager.save(word); 
		}
		else {
			return false;
		}
	}
	
	public LinkedList<Word> getWords() {
		return words;
	}
	
	public void fillWordsFromDB() {
		LinkedList<Word> words = DBManager.getWordsByListID(super.getID());
		if(words != null) {
			if(words.size() > 0) {
				this.words = words;
			}
		}
	}

}
