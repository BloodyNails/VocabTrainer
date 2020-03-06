package com.bloodynails;

public class List extends DBObj{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String description;
	private String lang1;
	private String lang2;
	
	public List (String description, String lang1, String lang2) {
		super(DBManager.getNextListID(), DBObjType.LIST);
		this.description = description;
		this.lang1 = lang1;
		this.lang2 = lang2;
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

}
