package com.bloodynails;

import java.util.LinkedHashMap;

public class VocabList extends DBObj{
	private static Long ID = DBManager.getNextListID();
	private String description;
	private String lang1;
	private String lang2;
	private LinkedHashMap<String, String> words;
	
	public VocabList (String description, String lang1, String lang2) {
		super(ID++, DBObjType.LIST);
		this.description = description;
		this.lang1 = lang1;
		this.lang2 = lang2;
		words = new LinkedHashMap<String, String>();
	}
	
	public VocabList(Long id, String description, String lang1, String lang2) {
		super(id, DBObjType.LIST);
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
	
	// redundant for now
	public boolean addWord(Word word) {
		if (word == null) 
			return false;
		else {
			words.put(word.getWordLang1(), word.getWordLang2());
			return true;
		}
	}
	
	// redundant as addWord has no use 
	public LinkedHashMap<String, String> getWords() {
		return words;
	}

}
