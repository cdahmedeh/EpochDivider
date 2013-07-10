package org.tronicsoft.epochdivider.core.type.timeblock;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;

public class TimeBlockTest {
	
	@Test
	public void createBasicTimeBlocksWithStartAndEndTimesAndUseGetters(){
		DateTime startTime = new DateTime(9543045345L);
		DateTime endTime = new DateTime(29543045345L);
		
		TimeBlock timeBlock = new TimeBlock(startTime, endTime);
		
		assertEquals(startTime, timeBlock.getStartTime());
		assertEquals(endTime, timeBlock.getEndTime());
	}
	
	@Test
	public void createBasicTimeBlocksWithStartAndEndTimesAndUseSetters(){
		DateTime oldStartTime = new DateTime(9543045345L);
		DateTime oldEndTime = new DateTime(29543045345L);
		
		TimeBlock timeBlock = new TimeBlock(oldStartTime, oldEndTime);
		
		DateTime newStartTime = new DateTime(12412L);
		DateTime newEndTime = new DateTime(12940124124124L);
		
		timeBlock.setStartTime(newStartTime);
		timeBlock.setEndTime(newEndTime);
		
		assertEquals(newStartTime, timeBlock.getStartTime());
		assertEquals(newEndTime, timeBlock.getEndTime());
	}
}
