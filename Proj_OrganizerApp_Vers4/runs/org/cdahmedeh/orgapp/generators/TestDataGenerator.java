package org.cdahmedeh.orgapp.generators;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.AllContextsContext;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.task.Task;
import org.joda.time.DateTime;

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
		taskList.add(task01);
		
		Task task02 = new Task("Data persistence prototype for Epoch Divider");
		task02.setContext(projectContext);
		task02.setDue(new DateTime(2013, 04, 30, 15, 00));
		taskList.add(task02);
		
		Task task03 = new Task("Read - Art of Unix Programming");
		task03.setContext(readingContext);
		taskList.add(task03);
		
		dataContainer.setContexts(contextList);
		dataContainer.setTasks(taskList);
		
		return dataContainer;
	}
}
