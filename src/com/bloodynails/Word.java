package com.bloodynails;

public class Word extends DBObj{
	private static long ID = DBManager.getNextWordID();
	private Long listID;
	private String wordLang1;
	private String wordLang2;
	
	public Word(Long listID, String wordLang1, String wordLang2) {
		super(ID++, DBObjType.WORD);
		this.listID = listID;
		this.wordLang1 = wordLang1;
		this.wordLang2 = wordLang2;
	}
	
	public Long getListID() {
		return this.listID;
	}
	
	public String getWordLang1() {
		return wordLang1;
	}
	
	public String getWordLang2() {
		return wordLang2;
	}
}
