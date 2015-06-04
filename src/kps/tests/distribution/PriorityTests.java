package kps.tests.distribution;

import static org.junit.Assert.*;
import kps.distribution.exception.InvalidPriorityException;
import kps.distribution.network.Priority;

import org.junit.Test;

public class PriorityTests {

	@Test
	public void parsesCorrectly(){
		assertEquals(Priority.fromString("international_air"), Priority.INTERNATIONAL_AIR);
		assertEquals(Priority.fromString("international air"), Priority.INTERNATIONAL_AIR);
		assertEquals(Priority.fromString("INTERNATIONAL AIR"), Priority.INTERNATIONAL_AIR);
		assertEquals(Priority.fromString("INTERNATIONAL STANDARD"), Priority.INTERNATIONAL_STANDARD);
		assertEquals(Priority.fromString("DOMESTIC STANDARD"), Priority.DOMESTIC_STANDARD);
		assertEquals(Priority.fromString("DOMESTIC AIR"), Priority.DOMESTIC_AIR);
	}
	
	@Test (expected = InvalidPriorityException.class) 
	public void throwsCorrectly(){
		Priority.fromString("international fake");
	}
}
