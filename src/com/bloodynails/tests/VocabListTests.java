package com.bloodynails.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.bloodynails.VocabLang;
import com.bloodynails.VocabList;
import com.bloodynails.VocabPair;
import com.bloodynails.VocabWord;

class VocabListTests {

	// fuck testing
	
	@Test
	void addWordTest() {
		VocabList l = new VocabList(0L, "testing list", new VocabPair(VocabLang.GERMAN, VocabLang.RUSSIAN));
		VocabWord w = new VocabWord(0L, 0L, "lang1", "lang2");
	}

}
