package org.jrichclient.richdock;


import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({  
	org.jrichclient.richdock.dockable.RunAll.class,
	org.jrichclient.richdock.dockingport.RunAll.class,
	org.jrichclient.richdock.dockingport.tabbar.RunAll.class,
	org.jrichclient.richdock.helper.RunAll.class
})
public class RunAll {
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.jrichclient.richdock");
		
		//$JUnit-BEGIN$
		suite.addTest(org.jrichclient.richdock.dockable.RunAll.suite());
		suite.addTest(org.jrichclient.richdock.dockingport.RunAll.suite());
		suite.addTest(org.jrichclient.richdock.dockingport.tabbar.RunAll.suite());
		suite.addTest(org.jrichclient.richdock.helper.RunAll.suite());
		//$JUnit-END$
		
		return suite;
	}

}
