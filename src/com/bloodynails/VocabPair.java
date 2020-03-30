package com.bloodynails;

public class VocabPair {
	private boolean valid;
	private VocabLang lang1;
	private VocabLang lang2;
	
	public VocabPair() {
		this.valid = false;
		this.lang1 = null;
		this.lang2 = null;
	}
	
	public VocabPair(VocabLang lang1, VocabLang lang2) {
		if(lang1 == lang2)
			throw new IllegalArgumentException("The selected languages should be different");
		if(lang1 == null || lang2 == null)
			throw new NullPointerException();
		this.valid = true;
		this.lang1 = lang1;
		this.lang2 = lang2;
	}
	
	public VocabLang getLang1() {
		return this.lang1;
	}
	
	public VocabLang getLang2() {
		return this.lang2;
	}
	
	public boolean isValid() {
		return this.valid;
	}
	
	public static VocabPair parseLangs(String lang1, String lang2) {
		switch (lang1.toLowerCase()) {
			case "german":
				switch (lang2.toLowerCase()) {
					case "russian":
						return new VocabPair(VocabLang.GERMAN, VocabLang.RUSSIAN);
					case "english":
						return new VocabPair(VocabLang.GERMAN, VocabLang.ENGLISH);
					default:
						return new VocabPair();
				}
			case "english":
				switch (lang2.toLowerCase()) {
					case "german":
						return new VocabPair(VocabLang.ENGLISH, VocabLang.GERMAN);
					case "russian":
						return new VocabPair(VocabLang.ENGLISH, VocabLang.RUSSIAN);
					default:
						return new VocabPair();
				}
			case "russian":
				switch (lang2.toLowerCase()) {
					case "german":
						return new VocabPair(VocabLang.RUSSIAN, VocabLang.GERMAN);
					case "english":
						return new VocabPair(VocabLang.RUSSIAN, VocabLang.ENGLISH);
					default:
						return new VocabPair();
				}
			default:
				return new VocabPair();
		}
	}
}
