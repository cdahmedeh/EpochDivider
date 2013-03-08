package org.cdahmedeh.orgapp.types.task;

import static org.junit.Assert.*;

import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.task.TypeConstants;
import org.junit.Test;

public class TaskTest {
	@Test public void taskTitleShouldBeBlankStringByDefault(){
		assertEquals("", new Task().getTitle());
	}
	@Test public void settingTaskTitleShouldRemoveWhiteSpaceBeforeAndAfter(){
		Task task = new Task();

		task.setTitle("test");
		assertEquals("test", task.getTitle());
		
		task.setTitle("te st");
		assertEquals("te st", task.getTitle());
		
		task.setTitle(" test ");
		assertEquals("test", task.getTitle());
		
		task.setTitle("test ");
		assertEquals("test", task.getTitle());
		
		task.setTitle(" test ");
		assertEquals("test", task.getTitle());
		
		task.setTitle(" 	test  	\r	\n   \t");
		assertEquals("test", task.getTitle());
		
		task.setTitle(" multiple word test ");
		assertEquals("multiple word test", task.getTitle());
	}
	@Test public void settingTaskTitleToNullShouldReallySetItToBlankString(){
		Task task = new Task();
		task.setTitle(null);
		assertEquals("", task.getTitle());
	}
	
	@Test public void taskHasCorrectDefaultMutabilitySettingWhenCreated(){
		assertEquals(TypeConstants.DEFAULT_MUTABILITY, new Task().getMutability());
	}
	@Test public void settingTaskMutabilityToNullShouldReallySetItToDefault(){
		Task task = new Task();
		task.setMutability(null);
		assertEquals(TypeConstants.DEFAULT_MUTABILITY, task.getMutability());
	}
	
	@Test public void taskHasCorrectDefaultPermissibilitySettingWhenCreated(){
		assertEquals(TypeConstants.DEFAULT_PRIORITY, new Task().getPriority());
	}
	@Test public void settingTaskPermissibilityToNullShouldReallySetItToDefault(){	
		Task task = new Task();
		task.setPriority(null);
		assertEquals(TypeConstants.DEFAULT_PRIORITY, task.getPriority()); 
	}
}
