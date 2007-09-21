package org.jrichclient.richdock.dockingport.tabbar;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.Test;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestRotation.class,
	TestTabComponent.class,
	TestTabBarDockingPort.class
})
public class RunAll {
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.jrichclient.richdock.dockingport.tabbar");
		
		//$JUnit-BEGIN$
		suite.addTest(TestRotation.suite());
		suite.addTest(TestTabComponent.suite());
		suite.addTest(TestTabBarDockingPort.suite());
		//$JUnit-END$
		
		return suite;
	}
}
