package org.jrichclient.richdock.dockingport;

import static org.junit.Assert.*;

import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import junit.framework.JUnit4TestAdapter;

import org.jrichclient.richdock.ContentLocationDockingPortTester;
import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.dockable.BasicDockable;
import org.jrichclient.richdock.icons.ImageResources;
import org.junit.Test;

public class TestScrollPaneDockingPort extends ContentLocationDockingPortTester {
	private final ScrollPaneDockingPort dockingPort;

// Constructors ****************************************************************
	
	public TestScrollPaneDockingPort() {
		this(new ScrollPaneDockingPort());
	}
	
	public TestScrollPaneDockingPort(ScrollPaneDockingPort dockingPort) {
		super(dockingPort, ScrollPaneDockingPort.LOCATIONNAME_CONTENT);
		
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
		assertFalse(dockingPort.getDisposeOnEmpty());
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
		
		ScrollPaneDockingPort original = new ScrollPaneDockingPort();
		original.dock(originalDockable, ScrollPaneDockingPort.LOCATIONNAME_CONTENT);
		original.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		original.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		
		ScrollPaneDockingPort copy = original.clone();
		assertEquals(testTitle, copy.getTitle());
		assertEquals(testIconFile, copy.getIconFile());
		assertEquals(testToolTipText, copy.getToolTipText());
		assertTrue(copy.getPopupMenu() instanceof JPopupMenu);
		assertEquals(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS, copy.getHorizontalScrollBarPolicy());
		assertEquals(JScrollPane.VERTICAL_SCROLLBAR_NEVER, copy.getVerticalScrollBarPolicy());
		
		assertEquals(1, copy.getDockableCount());
		Dockable copyDockable = copy.getDockable(ScrollPaneDockingPort.LOCATIONNAME_CONTENT);
		assertEquals(testTitle, copyDockable.getTitle());
		assertEquals(testIconFile, copyDockable.getIconFile());
		assertEquals(testToolTipText, copyDockable.getToolTipText());
		assertTrue(copyDockable.getPopupMenu() instanceof JPopupMenu);
		
		assertEquals(originalDockable.getPropertyChangeListeners().length,
			copyDockable.getPropertyChangeListeners().length);
		
		original.dispose();
		copy.dispose();
	}
	
// Dock/Undock *****************************************************************
	
	@Test
	public void testDockUndock() {
		testDockUndock(ScrollPaneDockingPort.LOCATIONNAME_CONTENT);
	}
	
	// Allow the test to be run with JUnit 3 test runners
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestScrollPaneDockingPort.class);
	}

}
