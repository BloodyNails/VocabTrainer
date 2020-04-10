package com.bloodynails.logging;

public class Logger {
	private static Counter counter = Counter.getInstance();
	
	public static void log(String s) {
		log(MessageType.INFO, s);
	}
	
	public static void log(MessageType t, String s) {
		if(s.contains("\n"))
			multiLinePrint(t,s);
		else
			print(t,s);
	}
	
	// TODO:
	// write s to file and save counter
	// add timestamp
	private static void print(MessageType t, String s) {
		System.out.println(counter.toString()+" [VocabLogger]["+t.toString()+"]: " + s);
	}
	
	private static void multiLinePrint(MessageType t, String s) {
		String[] lines = s.split("\n");

		for(Integer i = 0; i < lines.length; i++) {
			print(t, "x."+i.toString() + ": " +lines[i]);
		}
	}
}
