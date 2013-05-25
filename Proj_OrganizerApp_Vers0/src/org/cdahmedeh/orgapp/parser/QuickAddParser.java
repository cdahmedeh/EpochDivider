package org.cdahmedeh.orgapp.parser;

import java.util.ArrayList;
import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

public class QuickAddParser {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			String someText = scanner.next();
			QuickAddParser.parse(someText);
		}
	}
	
	public static void parse(String text) {
		String title = "";
	
		ArrayList<String> durationWords = new ArrayList<>();
		durationWords.add("for");
		
		ArrayList<String> timeWords = new ArrayList<>();
		timeWords.add("at");
		
		ArrayList<String> dateWords = new ArrayList<>();
		dateWords.add("on");
		
		ArrayList<String> todoWords = new ArrayList<>();
		todoWords.add("due");
		todoWords.add("for");
		
		System.out.println(text);
	}
}
