package org.cdahmedeh.orgapp.generators;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.AllContextsContext;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.context.NoContextContext;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * Contains method to generate some test data.
 * 
 * @author Ahmed El-Hajjar
 */
public class TestDataGenerator {
	public static DataContainer generateDataContainer(){
		DataContainer dataContainer = new DataContainer();
	
		ArrayList<Context> contextList = new ArrayList<>();
		
		contextList.add(new AllContextsContext());
		contextList.add(new NoContextContext());
		
		Context faithContext = new Context("Faith");
		contextList.add(faithContext);
		
		Context sleepContext = new Context("Sleep");
		contextList.add(sleepContext);
		
		Context relaxingContext = new Context("Relaxing");
		contextList.add(relaxingContext);
		
		Context exerciseContext = new Context("Exercise");
		contextList.add(exerciseContext);
		
		Context studyContext = new Context("Study");
		contextList.add(studyContext);
	
		Context coursesContext = new Context("Courses");
		contextList.add(coursesContext);
		
		Context miscContext = new Context("Misc");
		contextList.add(miscContext);
	
		Context projectContext = new Context("Projects");
		contextList.add(projectContext);
		
		Context readingContext = new Context("Reading");
		contextList.add(readingContext);
		
		ArrayList<Task> taskList = new ArrayList<>();
		
		Task task01 = new Task("Clean car");
		task01.setContext(miscContext);
		task01.setDue(DateTime.now().toDateMidnight().plusDays(1).toDateTime());
		task01.assignToTimeBlock(new TimeBlock(new DateTime(2013,04,25,13,30)));
		taskList.add(task01);
		
		Task task02 = new Task("Data persistence prototype for Epoch Divider");
		task02.setContext(projectContext);
		task02.setDue(new DateTime(2013, 04, 30, 15, 00));
		task02.assignToTimeBlock(new TimeBlock(new DateTime(2013,04,22,17,00)));
		task02.assignToTimeBlock(new TimeBlock(new DateTime(2013,04,22,23,00),new DateTime(2013,04,23,15,15)));
		taskList.add(task02);
		
		Task task03 = new Task("Read - Art of Unix Programming");
		task03.setContext(readingContext);
		taskList.add(task03);
		
		Task task04 = new Task("Test Contexts with No Contexts");
		task04.setContext(null);
		taskList.add(task04);
		
		dataContainer.loadContexts(contextList);
		dataContainer.loadTasks(taskList);
		
		dataContainer.setView(new View(new LocalDate(2013, 04, 21), new LocalDate(2013, 04, 27)));
		
		return dataContainer;
	}
}
