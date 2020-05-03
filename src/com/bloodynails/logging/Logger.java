package com.bloodynails.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
	private static final String datePattern = "HH:mm:ss";
	
	public static void log(Object o) {
		log(MessageType.INFO, o);
	}
	
	public static void log(MessageType t, Object o) {
		if(o.getClass() == String.class) {
			String s = (String) o;
			if(s.contains("\n")) {
				multiLinePrint(t, s);
			}
			else {
				print(t, s);
			}
		}
		else {
			print(t, o);
		}
	}
	
	// TODO:
	// write s to file and reset counter after every new file
	private static void print(MessageType t, Object o) {
		String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(datePattern)).toString();
		String s = "["+dateString+"]["+t.toString()+"]: " + o;
		System.out.println(s);
		FileManager.write(com.bloodynails.Config.logsPath, s);
	}

	private static void multiLinePrint(MessageType t, String s) {
		String[] lines = s.split("\n");
		for(Integer i = 0; i < lines.length; i++) {
			print(t, i.toString() + ": " +lines[i]);
		}
	}
}
