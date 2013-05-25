package org.cdahmedeh.orgapp.view;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.Test;

public class TestView {

	@Test
	public void testNumberOfDaysVisibleShouldRepresentDaysInBetweenTwoDatesInclusively(){
		View view = new View(new LocalDate(2013, 7, 11), new LocalDate(2013, 7, 18));
		assertEquals(8, view.getNumberOfDaysVisible());		
	}
	
	@Test
	public void testNumberOfDaysVisibleShouldRepresentDaysInBetweenSameDaysShouldBeOne(){
		View view = new View(new LocalDate(2013, 8, 30), new LocalDate(2013, 8, 30));
		assertEquals(1, view.getNumberOfDaysVisible());		
	}
}
