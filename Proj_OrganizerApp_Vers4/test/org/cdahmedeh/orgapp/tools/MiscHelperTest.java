package org.cdahmedeh.orgapp.tools;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for MiscHelper
 *  
 * @author Ahmed El-Hajjar
 */
public class MiscHelperTest {
	@Test public void safeTrimShouldRemoveOnlyWhiteSpaceBeforeAndAfterInputString(){
		assertEquals("test", MiscHelper.safeTrim("test"));
		assertEquals("te st", MiscHelper.safeTrim("te st"));
		assertEquals("test", MiscHelper.safeTrim(" test "));
		assertEquals("test", MiscHelper.safeTrim("test "));
		assertEquals("test", MiscHelper.safeTrim(" test"));
		assertEquals("test", MiscHelper.safeTrim(" test   "));
		assertEquals("test", MiscHelper.safeTrim("   test   "));
		assertEquals("test", MiscHelper.safeTrim("   test "));
		assertEquals("test", MiscHelper.safeTrim(" 	test  	\r	\n   \t"));
		assertEquals("multiple word test", 		MiscHelper.safeTrim(" multiple word test "));
	}
	
	@Test public void safeTrimShouldGiveBlankStringIfInputIsNull(){
		assertEquals("", MiscHelper.safeTrim(null));
	}
}
