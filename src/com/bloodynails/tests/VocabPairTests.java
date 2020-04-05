package com.bloodynails.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.bloodynails.VocabLang;
import com.bloodynails.VocabPair;

class VocabPairTests {
	
	// different langs
	VocabPair vpDEEN = new VocabPair(VocabLang.GERMAN, VocabLang.ENGLISH);
	VocabPair vpDERU = new VocabPair(VocabLang.GERMAN, VocabLang.RUSSIAN);
	
	VocabPair vpENDE = new VocabPair(VocabLang.ENGLISH, VocabLang.GERMAN);
	VocabPair vpENRU = new VocabPair(VocabLang.ENGLISH, VocabLang.RUSSIAN);
	
	VocabPair vpRUEN = new VocabPair(VocabLang.RUSSIAN, VocabLang.ENGLISH);
	VocabPair vpRUDE = new VocabPair(VocabLang.RUSSIAN, VocabLang.GERMAN);
	
	// same langs
	VocabPair vpDEDE = new VocabPair(VocabLang.GERMAN, VocabLang.GERMAN);
	VocabPair vpENEN = new VocabPair(VocabLang.ENGLISH, VocabLang.ENGLISH);
	VocabPair vpRURU = new VocabPair(VocabLang.RUSSIAN, VocabLang.RUSSIAN);
	
	String de = "German";
	String en = "English";
	String ru = "Russian";
	
	@Test
	void parseLangsTest() {
		// different langs
		assertEquals(vpDEEN.getLang1(), VocabPair.parseLangs(de, en).getLang1());
		assertEquals(vpDEEN.getLang2(), VocabPair.parseLangs(de, en).getLang2());
		assertTrue(vpDEEN.isValid());
		
		assertEquals(vpDERU.getLang1(), VocabPair.parseLangs(de, ru).getLang1());
		assertEquals(vpDERU.getLang2(), VocabPair.parseLangs(de, ru).getLang2());
		assertTrue(vpDERU.isValid());
		
		assertEquals(vpENDE.getLang1(), VocabPair.parseLangs(en, de).getLang1());
		assertEquals(vpENDE.getLang2(), VocabPair.parseLangs(en, de).getLang2());
		assertTrue(vpENDE.isValid());
		
		assertEquals(vpENRU.getLang1(), VocabPair.parseLangs(en, ru).getLang1());
		assertEquals(vpENRU.getLang2(), VocabPair.parseLangs(en, ru).getLang2());
		assertTrue(vpENRU.isValid());
		
		assertEquals(vpRUEN.getLang1(), VocabPair.parseLangs(ru, en).getLang1());
		assertEquals(vpRUEN.getLang2(), VocabPair.parseLangs(ru, en).getLang2());
		assertTrue(vpRUEN.isValid());
		
		assertEquals(vpRUDE.getLang1(), VocabPair.parseLangs(ru, de).getLang1());
		assertEquals(vpRUDE.getLang2(), VocabPair.parseLangs(ru, de).getLang2());
		assertTrue(vpRUDE.isValid());
		
		// same langs
		assertEquals(vpDEDE.getLang1(), VocabPair.parseLangs(de, de).getLang1());
		assertEquals(vpDEDE.getLang2(), VocabPair.parseLangs(de, de).getLang2());
		assertTrue(vpDEDE.isValid());
		
		assertEquals(vpENEN.getLang1(), VocabPair.parseLangs(en, en).getLang1());
		assertEquals(vpENEN.getLang2(), VocabPair.parseLangs(en, en).getLang2());
		assertTrue(vpENEN.isValid());
		
		assertEquals(vpRURU.getLang1(), VocabPair.parseLangs(ru, ru).getLang1());
		assertEquals(vpRURU.getLang2(), VocabPair.parseLangs(ru, ru).getLang2());
		assertTrue(vpRURU.isValid());
	}
	
	@Test
	void emptyConstructorTest() {
		VocabPair vpNull = new VocabPair();
		
		assertNull(vpNull.getLang1());
		assertNull(vpNull.getLang2());
	}
}
