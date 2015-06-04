package kps.tests.distribution;

import static org.junit.Assert.assertEquals;
import kps.distribution.network.TransportType;

import org.junit.Test;

public class TransportTypeTests {
	@Test
	public void parsesLandSeaAir(){
		assertEquals(TransportType.fromString("AIR"), TransportType.AIR);
		assertEquals(TransportType.fromString("LAND"), TransportType.LAND);
		assertEquals(TransportType.fromString("SEA"), TransportType.SEA);
	}

	@Test
	public void parsesAnyCase(){
		assertEquals(TransportType.fromString("AIR"), TransportType.AIR);
		assertEquals(TransportType.fromString("air"), TransportType.AIR);
		assertEquals(TransportType.fromString("air "), TransportType.AIR);
		assertEquals(TransportType.fromString("aIr"), TransportType.AIR);
	}
}
