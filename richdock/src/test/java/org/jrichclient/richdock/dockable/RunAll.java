package org.jrichclient.richdock.dockable;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.Test;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({  
	TestBasicDockable.class
})
public class RunAll {
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.jrichclient.richdock.dockable");
		
		//$JUnit-BEGIN$
		suite.addTest(TestBasicDockable.suite());
		//$JUnit-END$
		
		return suite;
	}

}
