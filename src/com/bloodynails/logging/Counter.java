package com.bloodynails.logging;

public class Counter {
	private static Counter instance;
	private static boolean isSaved = false;
	private static int count;
	
	public static Counter getInstance() {
		if(instance == null && !isSaved) {
			instance = new Counter();
		}
		else if (instance == null && isSaved){
			// TODO: set c 
			int c = 0;
			instance = new Counter(c);
		}
		return instance;
	}
	
	private Counter() {
		count = 0;
	}
	
	private Counter(int c) {
		count = c;
	}
	
	private int getCount() {
		return count++;
	}
	
	@Override
	public String toString() {
		// to be able to change the string which is returned
		int c = this.getCount();
		String s = "#"+Integer.toString(c);
		return s;
	}
	
	public boolean save() {
		// TODO save instance to db;
		return false;
	}
	
	public void reset() {
		instance = new Counter();
	}
}
