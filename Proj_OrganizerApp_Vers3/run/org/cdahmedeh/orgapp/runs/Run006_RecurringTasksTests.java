package org.cdahmedeh.orgapp.runs;

import org.cdahmedeh.orgapp.types.recurrence.DailyRecurrence;
import org.cdahmedeh.orgapp.types.task.RecurrentTaskInstance;
import org.cdahmedeh.orgapp.types.task.RecurrentTaskTemplate;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.LocalDate;

/**
 * Testing own recurrence types.
 * 
 * @author cdahmedeh
 */
public class Run006_RecurringTasksTests {
	public static void main(String[] args) throws Exception {
		LocalDate start = new LocalDate(2013, 01, 01);
		 
		DailyRecurrence rec = new DailyRecurrence(start);
		rec.setAmount(10);
		rec.addExceptions(new LocalDate(2013,01,03));
//		rec.setEnd(new LocalDate(2013, 10, 10));
		
		RecurrentTaskTemplate rTaskI = new RecurrentTaskTemplate("Some task");
		rTaskI.setRecurrence(rec);
		rTaskI.assignToTimeBlock(new TimeBlock());
		
		for (RecurrentTaskInstance task: rTaskI.generateRecurrentTasks(null, null)){
			System.out.println(task.getTitle());
			System.out.println(task.getFirstTimeBlockFromNow().getStart());
		}
	}
}
