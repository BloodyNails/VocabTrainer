package com.bloodynails;

public enum VocabLang {
	GERMAN("German"),
	ENGLISH("English"),
	RUSSIAN("Russian");
	
	private String language;
	
	private VocabLang(String language) {
		this.language = language;
	}
	
	@Override
	public String toString() {
		return language;
	}
}
