package com.bloodynails.logging;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class Logger {
	public static void printRequestMap(HttpServletRequest request) {
		print("request Map: ");
		Map<String, String[]> paraMap = request.getParameterMap();
		for(Map.Entry<String, String[]> entry : paraMap.entrySet()) {
			print(" key:");
			print("   " + entry.getKey());
			print(" values:");
			for(int i = 0; i < entry.getValue().length; i++) {
				print("   " + entry.getValue()[i]);
			}
		}
	}
	
	public static void log(String s) {
		print(s);
	}
	
	private static void print(String s) {
		System.out.println("[VocabLogger]: " + s);
		// TODO:
		// add timestamp
		// add actual logging to log file
		// add problem serverity: [INFO] / [WARNING] / [ERROR]
	}
}
