package org.cdahmedeh.orgapp.parser;

import java.util.ArrayList;
import java.util.Scanner;

import org.joda.time.DateTime;

public class DateParser {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			String someText = scanner.nextLine();
			DateParser.parse(someText);
		}
	}
	
	public static void parse(String text) {
		DateTime dt = null;
		String input = text.trim();
		
		if (input.equals("today")){
			dt = new DateTime().toDateMidnight().plusDays(0).toDateTime();
		} else if (input.equals("tomorrow")){
			dt = new DateTime().toDateMidnight().plusDays(1).toDateTime();
		} else if (input.equals("after tomorrow")){
			dt = new DateTime().toDateMidnight().plusDays(2).toDateTime();
		}
		
		System.out.println(dt);
	}
}
