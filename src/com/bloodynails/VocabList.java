package com.bloodynails;

import java.util.LinkedList;

import com.bloodynails.database.DBManager;
import com.bloodynails.database.DBObj;
import com.bloodynails.database.DBObjType;

public class VocabList extends DBObj{
	private String description;
	private VocabPair languages;
	private LinkedList<VocabWord> words;
	
	public VocabList (String description, VocabPair languages) {
		super(DBManager.getNextListID(), DBObjType.LIST);
		this.description = description;
		this.languages = languages;
		words = new LinkedList<VocabWord>();
	}
	
	public VocabList(Long listID, String description, VocabPair languages) {
		super(listID, DBObjType.LIST);
		this.description = description;
		this.languages = languages;
		words = new LinkedList<VocabWord>();
	}
	
	@Override
	public String toString() {
		return "VocabList:"+"\nlistID: "+ID+"\ndescription: "+description+"\nlanguages: "+languages.getLang1().toString()
				+", "+languages.getLang2().toString();
	}
	
	public String getDescription() {
		return description;
	}
	
	public VocabPair getLangs() {
		return this.languages;
	}
	
	public VocabLang getLang1() {
		return languages.getLang1();
	}

	public VocabLang getLang2() {
		return languages.getLang2();
	}
	
	public boolean addWord(VocabWord word) {
		if (word != null && words != null) {
			return words.add(word);
		}
		else {
			return false;
		}
	}
	
	public VocabWord deleteWordByID(Long wordID) {
		for(int i = 0; i < words.size(); i++) {
			if(words.get(i).getID() == wordID) {
				return words.remove(i);
			}
		}
		return null;
	}
	
	public LinkedList<VocabWord> getWords() {
		return words;
	}
	
	public VocabList fillWordsFromDB() {
		LinkedList<VocabWord> words = DBManager.getWordsByListID(super.getID());
		if(words != null) {
			if(words.size() > 0) {
				this.words = words;
			}
		}
		return this;
	}

}
