package com.bloodynails.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// TODO
// I intended this class to write the Counter to a file and also to convert tables into textfiles
// it is quite complicated to do this on a server though
// maybe later
public class FileManager {
	
	public static boolean write(String f, String s) {
		return false;
	}
	
	public static String read(String path) throws FileNotFoundException {
		String s = new String();
		File f = new File(path);
		Scanner scanner = new Scanner(f);
		
		while(scanner.hasNext()) {
			String line = scanner.nextLine();
			s += line;
		}
		
		return s;
	}
	
}
