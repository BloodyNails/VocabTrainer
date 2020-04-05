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
		return this.language;
	}
	
	public static VocabLang parseLang(String s) {
		
		switch (s.toLowerCase()) {
			case "german":
				return GERMAN;
			case "english":
				return ENGLISH;
			case "russian":
				return RUSSIAN;
			default:
				return null;
		}
	}
}
