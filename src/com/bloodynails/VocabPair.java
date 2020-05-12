package com.bloodynails;

public class VocabPair {
	private VocabLang lang1;
	private VocabLang lang2;
	
	public VocabPair(VocabLang lang1, VocabLang lang2) {
		if(lang1 == null) throw new NullPointerException("lang1 must not be null");
		if(lang2 == null) throw new NullPointerException("lang2 must not be null");
		
		this.lang1 = lang1;
		this.lang2 = lang2;
	}
	
	public VocabLang getLang1() {
		return this.lang1;
	}
	
	public VocabLang getLang2() {
		return this.lang2;
	}
	
	public boolean contains(VocabLang lang) {
		return (lang == lang1 || lang == lang2);
	}
	
	public static VocabPair parseLangs(String lang1, String lang2) {
		if(lang1 == null || lang2 == null) return null;
		VocabLang vl1 = VocabLang.parseLang(lang1);
		VocabLang vl2 = VocabLang.parseLang(lang2);
		if(vl1 == null || vl2 == null) return null;
		return new VocabPair(vl1, vl2);
	}
	
	/**
	 * 
	 * @param vp is the VocabPair you want to compare to
	 * @return <b>true</b> if the VocabPairs contain the same languages <br>
	 * <b>false</b> if the VocabPairs do not contain the same languages
	 */
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
