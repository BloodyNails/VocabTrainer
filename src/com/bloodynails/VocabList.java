package com.bloodynails;

import java.util.LinkedList;

import com.bloodynails.database.DBManager;
import com.bloodynails.database.DBObj;
import com.bloodynails.database.DBObjType;

public class VocabList extends DBObj{
	private String description;
	private VocabPair languages;
	
	public VocabList (String description, VocabPair languages) {
		super(DBManager.getNextListID(), DBObjType.LIST);
		
		if(description == null) throw new NullPointerException("description must not be null");
		if (description.isEmpty()) throw new IllegalArgumentException("description of list #" + super.getID() + " is empty");
		if(languages == null) throw new NullPointerException("languages must not be null");
		
		this.description = description;
		this.languages = languages;
	}
	
	public VocabList(Long listID, String description, VocabPair languages) {
		super(listID, DBObjType.LIST);
		
		if(listID == null) throw new NullPointerException("listID must not be null");
		if(listID < 0) throw new IllegalArgumentException("listID must be equal to or greater than 0");
		if(description == null) throw new NullPointerException("description must not be null");
		if (description.isEmpty()) throw new IllegalArgumentException("description of list #" + super.getID() + " is empty");
		if(languages == null) throw new NullPointerException("languages must not be null");
		
		this.description = description;
		this.languages = languages;
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
	
	public LinkedList<VocabWord> getWords() {
		return DBManager.getWordsByListID(super.ID);
	}
}
