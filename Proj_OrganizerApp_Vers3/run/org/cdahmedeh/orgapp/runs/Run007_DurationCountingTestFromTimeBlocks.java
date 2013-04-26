package org.cdahmedeh.orgapp.runs;

import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.DateTime;

public class Run007_DurationCountingTestFromTimeBlocks {
	public static void main(String[] args) {
		Task task1 = new Task("");
		task1.assignToTimeBlock(new TimeBlock(new DateTime(2013, 01, 01, 18, 00), new DateTime(2013, 01, 02, 18, 00)));
//		System.out.println(task1.getDurationPassed(new DateTime(2013,01,02,00,00)).getStandardHours());
		System.out.println(task1.getDurationScheduled(new DateTime(2013,01,02,00,00)).getStandardHours());
		System.out.println(task1.getDurationPassedSince(new DateTime(2013,01,01,23,00) ,new DateTime(2013,01,02,18,01)).getStandardHours());
	}
}
