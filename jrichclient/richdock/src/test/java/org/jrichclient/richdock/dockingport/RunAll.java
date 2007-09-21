package org.jrichclient.richdock.dockingport;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({  
	TestFrameDockingPort.class,
	TestInternalFrameDockingPort.class,
	TestBorderLayoutDockingPort.class,
	TestSplitPaneDockingPort.class,
	TestMultiSplitDockingPort.class,
	TestScrollPaneDockingPort.class,
	TestDesktopPaneDockingPort.class,
	TestViewDockingPort.class,
	TestTabbedPaneDockingPort.class,
	TestScrollArrowDockingPort.class
})
public class RunAll {
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.jrichclient.richdock.dockingport");
		
		//$JUnit-BEGIN$
		suite.addTest(TestFrameDockingPort.suite());
		suite.addTest(TestInternalFrameDockingPort.suite());
		suite.addTest(TestBorderLayoutDockingPort.suite());
		suite.addTest(TestSplitPaneDockingPort.suite());
		suite.addTest(TestMultiSplitDockingPort.suite());
		suite.addTest(TestScrollPaneDockingPort.suite());
		suite.addTest(TestDesktopPaneDockingPort.suite());
		suite.addTest(TestViewDockingPort.suite());
		suite.addTest(TestTabbedPaneDockingPort.suite());
		suite.addTest(TestScrollArrowDockingPort.suite());
		//$JUnit-END$
		
		return suite;
	}

}
