package org.cdahmedeh.orgapp.runs;

import java.util.Scanner;

import org.joda.time.DateTime;

import com.mdimension.jchronic.Chronic;
import com.mdimension.jchronic.utils.Span;

public class Run001 {
	public static void main(String[] args) {
		while (true){
			try {
				Scanner scanner = new Scanner(System.in);
				
				Span parse = Chronic.parse(scanner.nextLine());
				
				DateTime parsed = new DateTime(parse.getBeginCalendar());
				
				System.out.println(parse);
				System.out.println(parsed.toString());
				System.out.println();
			} catch (NullPointerException e){
				
			}
		}
	}
}