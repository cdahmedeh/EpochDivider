package org.cdahmedeh.orgapp.runs;

import org.joda.time.Duration;

public class Run002 {
	public static void main(String[] args) {
		Duration duration = Duration.standardMinutes(180);
		
		System.out.println(duration.getStandardMinutes());
	}
}
