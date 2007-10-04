package org.jrichclient.richdock.dockingport;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import static org.jrichclient.richdock.UnitTestUtils.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import junit.framework.JUnit4TestAdapter;

import org.jrichclient.richdock.DockingPortTester;
import org.junit.*;

public class TestDesktopDockingPort extends DockingPortTester<Integer> {
	private final DesktopDockingPort dockingPort;

// Constructor *****************************************************************
	
	public TestDesktopDockingPort() {
		this(new DesktopDockingPort());
	}
	
	public TestDesktopDockingPort(DesktopDockingPort dockingPort) {
		super(dockingPort);
		
		this.dockingPort = dockingPort;
		dockingPort.setExitOnDispose(false);
	}
		
// Test Constructors ***********************************************************
	
	@Test
	public void testConstructor() {
		assertNull(dockingPort.getSize());
		assertNull(dockingPort.getLocation());
		assertEquals("", dockingPort.getTitle());
		assertNull(dockingPort.getIconFile());
		assertNull(dockingPort.getToolTipText());
		assertNull(dockingPort.getPopupMenu());
		assertFalse(dockingPort.isDragable());
		assertFalse(dockingPort.isFloatable());
		assertFalse(dockingPort.isDropable());
		assertNull(dockingPort.getDockingPort());
		assertNull(dockingPort.getComponent());
		assertEquals(0, dockingPort.getDockableCount());
		assertTrue(dockingPort.canClose());
		assertTrue(dockingPort.getDisposeOnEmpty());
		assertFalse(dockingPort.isDisposed());
	}
	
// Clone ***********************************************************************
	
	@Test
	public void testClone() throws CloneNotSupportedException {
		DesktopDockingPort original = new DesktopDockingPort();
		original.dock(new FrameDockingPort(), 0);
		assertEquals(1, original.getDockableCount());
		
		DesktopDockingPort copy = original.clone();
		assertEquals(1, copy.getDockableCount());
	}
	
// Dragable ********************************************************************
	
	@Override
	@Test(expected=IllegalArgumentException.class)
	public void testDragable() {
		dockingPort.setDragable(true);
	}
	
// Floatable *******************************************************************
	
	@Override
	@Test(expected=IllegalArgumentException.class)
	public void testFloatable() {
		dockingPort.setFloatable(true);
	}
	
// Dropable ********************************************************************
	
	@Override
	@Test(expected=IllegalArgumentException.class)
	public void testDropable() {
		dockingPort.setDropable(true);
	}
	
// Dock/Undock *****************************************************************
	
	@Test
	public void testDockUndock() {
		testDockUndock(0, new FrameDockingPort());
	}
	
// ExitOnDispose ***************************************************************
	
	@Test
	public void exitOnDispose() {
		boolean oldExitOnDispose = dockingPort.getExitOnDispose();
		boolean newExitOnDispose = !oldExitOnDispose;
		
		PropertyChangeListener listener = 
			createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(
			DesktopDockingPort.PROPERTYNAME_EXIT_ON_DISPOSE, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			DesktopDockingPort.PROPERTYNAME_EXIT_ON_DISPOSE, 
			oldExitOnDispose, newExitOnDispose));
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			DesktopDockingPort.PROPERTYNAME_EXIT_ON_DISPOSE, 
			newExitOnDispose, oldExitOnDispose));
		
		replay(listener);
		
		dockingPort.setExitOnDispose(newExitOnDispose);
		assertEquals(newExitOnDispose, dockingPort.getExitOnDispose());
		
		dockingPort.setExitOnDispose(oldExitOnDispose);
		assertEquals(oldExitOnDispose, dockingPort.getExitOnDispose());
		
		dockingPort.removePropertyChangeListener(
			DesktopDockingPort.PROPERTYNAME_EXIT_ON_DISPOSE, listener);
		verify(listener);
	}
	
	// Allow the test to be run with JUnit 3 test runners
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestDesktopDockingPort.class);
	}

}
