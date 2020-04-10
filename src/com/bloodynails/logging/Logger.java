package com.bloodynails.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
	private static Counter counter = Counter.getInstance();
	private static LocalDateTime date;
	private static final String datePattern = "yyyy.MM.dd HH:mm:ss";
	
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
		date = LocalDateTime.now();
		date.format(DateTimeFormatter.ofPattern(datePattern));
		System.out.println(counter.toString()+" ["+date.toString()+"]["+t.toString()+"]: " + s);
	}
	
	private static void multiLinePrint(MessageType t, String s) {
		String[] lines = s.split("\n");

		for(Integer i = 0; i < lines.length; i++) {
			print(t, "x."+i.toString() + ": " +lines[i]);
		}
	}
}
