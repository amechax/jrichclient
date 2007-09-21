package org.jrichclient.richdock.helper;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.Test;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestDragHelper.class
})
public class RunAll {
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.jrichclient.richdock.helper");
		
		//$JUnit-BEGIN$
		suite.addTest(TestDragHelper.suite());
		//$JUnit-END$
		
		return suite;
	}

}
