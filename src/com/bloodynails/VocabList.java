package com.bloodynails;

import java.util.LinkedList;

import com.bloodynails.database.DBManager;
import com.bloodynails.database.DBObj;
import com.bloodynails.database.DBObjType;

public class VocabList extends DBObj{
	private String description;
	private VocabPair languages;
	private LinkedList<VocabWord> words;
	
	public VocabList() {
		// only needed when a VocabList must be initialized!
		super(-1L, DBObjType.LIST);
		this.description = "";
		this.languages = new VocabPair();
		words = new LinkedList<VocabWord>();
	}
	
	public VocabList (String description, VocabPair languages) {
		super(DBManager.getNextListID(), DBObjType.LIST);
		this.description = description;
		this.languages = languages;
		words = new LinkedList<VocabWord>();
		fillWordsFromDB();
	}
	
	public VocabList(Long listID, String description, VocabPair languages) {
		super(listID, DBObjType.LIST);
		this.description = description;
		this.languages = languages;
		words = new LinkedList<VocabWord>();
		fillWordsFromDB();
	}
	
	public String getDescription() {
		return description;
	}
	
	public VocabLang getLang1() {
		return languages.getLang1();
	}

	public VocabLang getLang2() {
		return languages.getLang2();
	}
	
	public boolean addWord(VocabWord word) {
		if (word != null && words != null) {
			words.add(word);
			return DBManager.save(word); 
		}
		else {
			return false;
		}
	}
	
	public LinkedList<VocabWord> getWords() {
		return words;
	}
	
	public void fillWordsFromDB() {
		LinkedList<VocabWord> words = DBManager.getWordsByListID(super.getID());
		if(words != null) {
			if(words.size() > 0) {
				this.words = words;
			}
		}
	}

}
