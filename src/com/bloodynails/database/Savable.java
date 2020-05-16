package com.bloodynails.database;

public interface Savable {
	boolean save();
	Boolean isSaved();
	boolean delete();
}
