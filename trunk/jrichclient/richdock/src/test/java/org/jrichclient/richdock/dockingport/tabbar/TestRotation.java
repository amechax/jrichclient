package org.jrichclient.richdock.dockingport.tabbar;

import static org.junit.Assert.assertEquals;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

public class TestRotation {
	
	@Test
	public void testRotation() {
		int count = 0;
		for (Rotation rotation : Rotation.values())
			assertEquals(count++, rotation.ordinal());
	}
	
	// Allow the test to be run with JUnit 3 test runners
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestRotation.class);
	}

}
