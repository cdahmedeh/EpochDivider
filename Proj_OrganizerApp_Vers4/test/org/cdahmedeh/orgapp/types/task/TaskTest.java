package org.cdahmedeh.orgapp.types.task;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TaskTest {
	@Test public void settingTitleInConstructorShouldSetTitle(){
		assertEquals("Title Test", new Task("Title Test").getTitle());
	}

	@Test public void settingTitleInConstructorAsNullShouldReallySetItToBlankString(){
		Task Task = new Task(null);
		assertEquals("", Task.getTitle());
	}

	@Test public void settingTitleWithSetTitleMethodShouldAlsoWork(){
		Task task = new Task("");
		task.setTitle("Title Test");
		assertEquals("Title Test", task.getTitle());
	}

	@Test public void settingTitleWithSetTitleMethodShouldRemoveTrailingWhiteSpace(){
		Task task = new Task("");
		task.setTitle(" \n  Title Test With Whitespace  ");
		assertEquals("Title Test With Whitespace", task.getTitle());
	}
	
	@Test public void settingTitleAsNullShouldReallySetItToBlankString(){
		Task task = new Task("");
		task.setTitle(null);
		assertEquals("", task.getTitle());
	}
}
