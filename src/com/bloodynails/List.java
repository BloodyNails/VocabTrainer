package com.bloodynails;

import java.util.LinkedHashMap;

public class List extends DBObj{
	private String description;
	private String lang1;
	private String lang2;
	private LinkedHashMap<String, String> words;
	
	public List (String description, String lang1, String lang2) {
		super(DBManager.getNextListID(), DBObjType.LIST);
		this.description = description;
		this.lang1 = lang1;
		this.lang2 = lang2;
		words = new LinkedHashMap<String, String>();
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
		if (word == null) 
			return false;
		else {
			words.put(word.getWordLang1(), word.getWordLang2());
			return true;
		}
	}
	
	public LinkedHashMap<String, String> getWords() {
		return words;
	}

}
