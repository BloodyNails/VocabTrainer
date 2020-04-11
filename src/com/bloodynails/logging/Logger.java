package com.bloodynails.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
	// TODO: pattern is not printed as it is declared here
	private static final String datePattern = "yyyy.MM.dd'-'HH:mm:ss";
	
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
	// write s to file and reset counter after every new file
	private static void print(MessageType t, String s) {
		String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(datePattern)).toString();
		System.out.println("["+dateString+"]["+t.toString()+"]: " + s);
	}
	
	private static void multiLinePrint(MessageType t, String s) {
		String[] lines = s.split("\n");

		for(Integer i = 0; i < lines.length; i++) {
			print(t, i.toString() + ": " +lines[i]);
		}
	}
}
