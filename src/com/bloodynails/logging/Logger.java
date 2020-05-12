package com.bloodynails.logging;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
	
	private static final String datePattern = "HH:mm:ss";
	private static final PrintStream printStream = new PrintStream(System.out, true, Charset.forName("UTF-8"));
	
	
	public static void log(Object obj) {
		log(MessageType.INFO, obj);
	}
	
	public static void log(MessageType type, Object obj) {
		if(obj == null || type == null) return;
		if(obj.getClass() == String.class) {
			String str = (String) obj;
			if(str.contains("\n")) {
				multiLinePrint(type, str);
			}
			else {
				print(type, str);
			}
		}
		else {
			print(type, obj);
		}
	}
	
	// TODO: write str to file
	private static void print(MessageType type, Object obj) {
		String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(datePattern)).toString();
		String s = "[" + dateString + "][" + type.toString() + "]: " + obj;
		printStream.println(s);
	}

	private static void multiLinePrint(MessageType type, String str) {
		String[] lines = str.split("\n");
		for(int i = 0; i < lines.length; i++) {
			print(type, i + ": " + lines[i]);
		}
	}
}
