package org.cdahmedeh.orgapp.generators;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.task.Task;

/**
 * Contains method to generate some test data.
 * 
 * @author Ahmed El-Hajjar
 */
public class TestDataGenerator {
	public static DataContainer generateDataContainer(){
		DataContainer dataContainer = new DataContainer();
		dataContainer.setContexts(generateContextList());
		dataContainer.setTasks(generateTaskList());
		return dataContainer;
	}
	
	private static ArrayList<Context> generateContextList(){
		ArrayList<Context> contextList = new ArrayList<>();
		
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
		
		Context blankContext = new Context("Blank");
		contextList.add(blankContext);
		
		return contextList;
	}
	
	private static ArrayList<Task> generateTaskList(){
		ArrayList<Task> taskList = new ArrayList<>();
		
		Task task01 = new Task("Clean car");
		taskList.add(task01);
		
		Task task02 = new Task("Data persistence prototype for Epoch Divider");
		taskList.add(task02);
		
		Task task03 = new Task("Read - Art of Unix Programming");
		taskList.add(task03);
		
		return taskList;
	}
}
