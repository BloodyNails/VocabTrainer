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
		if(lang1 != null && lang2 != null) {
			this.valid = true;
			this.lang1 = lang1;
			this.lang2 = lang2;
		}
		else {
			throw new NullPointerException("Both languages should not be null");
		}
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
	
	public boolean contains(VocabLang lang) {
		return (lang == lang1 || lang == lang2);
	}
	
	public static VocabPair parseLangs(String lang1, String lang2) {
		VocabLang vl1 = VocabLang.parseLang(lang1);
		VocabLang vl2 = VocabLang.parseLang(lang2);
		if(vl1 != null && vl2 != null) {
			return new VocabPair(vl1, vl2);
		}
		else {
			return new VocabPair();
		}
	}
	
	public boolean compareTo(VocabPair vp) {
		if(lang1 == vp.getLang1() && lang2 == vp.getLang2()) {
			return true;
		}
		else if(lang1 == vp.getLang2() && lang2 == vp.getLang1()) {
			return true;
		}
		else {
			return false;
		}
				
	}
}
