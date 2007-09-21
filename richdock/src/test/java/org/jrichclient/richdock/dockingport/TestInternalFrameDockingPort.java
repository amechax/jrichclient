package org.jrichclient.richdock.dockingport;

import static org.junit.Assert.*;

import javax.swing.JButton;
import javax.swing.JPopupMenu;

import org.jrichclient.richdock.ContentLocationDockingPortTester;
import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.dockable.BasicDockable;
import org.jrichclient.richdock.icons.ImageResources;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;

public class TestInternalFrameDockingPort extends ContentLocationDockingPortTester {
	private final InternalFrameDockingPort dockingPort;
	
// Constructor *****************************************************************
		
	public TestInternalFrameDockingPort() {
		this(new InternalFrameDockingPort());
	}
		
	public TestInternalFrameDockingPort(InternalFrameDockingPort dockingPort) {
		super(dockingPort, InternalFrameDockingPort.LOCATIONNAME_CONTENT);
		
		this.dockingPort = dockingPort;
	}
	
// Test Constructor ************************************************************
	
	@Test
	public void testConstructor() {
		assertEquals("", dockingPort.getTitle());
		assertNull(dockingPort.getIconFile());
		assertNull(dockingPort.getToolTipText());
		assertNull(dockingPort.getPopupMenu());
		assertTrue(dockingPort.isDragable());
		assertTrue(dockingPort.isFloatable());
		assertFalse(dockingPort.isDropable());
		assertNull(dockingPort.getDockingPort());
		assertEquals(0, dockingPort.getDockableCount());
		assertTrue(dockingPort.canClose());
		assertTrue(dockingPort.getDisposeOnEmpty());
		assertFalse(dockingPort.isDisposed());
	}

// Clone ***********************************************************************
	
	@Test
	public void testClone() throws CloneNotSupportedException {
		JButton testContent = new JButton("Test Button");
		String testTitle = "Test Title";
		String testIconFile = ImageResources.GLOBE_IMAGE;
		String testToolTipText = "Test ToolTipText";
		JPopupMenu testPopupMenu = new JPopupMenu();
		
		BasicDockable originalDockable = new BasicDockable(testContent,
			testTitle, testIconFile, testToolTipText, testPopupMenu);
		
		InternalFrameDockingPort original = new InternalFrameDockingPort();
		original.dock(originalDockable, InternalFrameDockingPort.LOCATIONNAME_CONTENT);
		
		InternalFrameDockingPort copy = original.clone();
		assertEquals(testTitle, copy.getTitle());
		assertEquals(testIconFile, copy.getIconFile());
		assertEquals(testToolTipText, copy.getToolTipText());
		assertTrue(copy.getPopupMenu() instanceof JPopupMenu);
		
		assertEquals(1, copy.getDockableCount());
		Dockable copyDockable = copy.getDockable(InternalFrameDockingPort.LOCATIONNAME_CONTENT);
		assertEquals(testTitle, copyDockable.getTitle());
		assertEquals(testIconFile, copyDockable.getIconFile());
		assertEquals(testToolTipText, copyDockable.getToolTipText());
		assertTrue(copyDockable.getPopupMenu() instanceof JPopupMenu);
		
		assertEquals(originalDockable.getPropertyChangeListeners().length,
			copyDockable.getPropertyChangeListeners().length);
		
		original.dispose();
		copy.dispose();
	}

// Dragable ********************************************************************
	
	@Override
	@Test(expected=IllegalArgumentException.class)
	public void testDragable() {
		super.testDragable();
	}
	
// Dock/Undock *****************************************************************
	
	@Test
	public void testDockUndock() {
		testDockUndock(InternalFrameDockingPort.LOCATIONNAME_CONTENT);
	}

	// Allow the test to be run with JUnit 3 test runners
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestInternalFrameDockingPort.class);
	}

}
