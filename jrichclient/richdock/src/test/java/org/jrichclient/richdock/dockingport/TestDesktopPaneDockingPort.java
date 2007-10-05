package org.jrichclient.richdock.dockingport;

import static org.junit.Assert.*;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JPopupMenu;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPortTester;
import org.jrichclient.richdock.dockable.BasicDockable;
import org.jrichclient.richdock.icons.ImageResources;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;

public class TestDesktopPaneDockingPort extends DockingPortTester<Integer> {
	private final DesktopPaneDockingPort dockingPort;
	
// Constructor *****************************************************************
	
	public TestDesktopPaneDockingPort() {
		this(new DesktopPaneDockingPort());
	}
	
	public TestDesktopPaneDockingPort(DesktopPaneDockingPort dockingPort) {
		super(dockingPort);
		
		this.dockingPort = dockingPort;
	}
	
// Test Constructor ************************************************************
	
	@Test
	public void testConstructor1() {
		assertEquals("", dockingPort.getTitle());
		assertNull(dockingPort.getIconFile());
		assertNull(dockingPort.getToolTipText());
		assertNull(dockingPort.getPopupMenu());
		assertTrue(dockingPort.isDragable());
		assertTrue(dockingPort.isFloatable());
		assertTrue(dockingPort.isDropable());
		assertNull(dockingPort.getDockingPort());
		assertEquals(0, dockingPort.getDockableCount());
		assertTrue(dockingPort.canClose());
		assertFalse(dockingPort.getDisposeOnEmpty());
		assertFalse(dockingPort.isDisposed());
	}
	
	@Test
	public void testConstructor2() {
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setSize(new Dimension(640, 480));
		
		String testTitle = "Test Title";
		DesktopPaneDockingPort dockingPort = 
			new DesktopPaneDockingPort(desktopPane);
		
		dockingPort.setTitle(testTitle);
		
		assertEquals(testTitle, dockingPort.getTitle());
		assertNull(dockingPort.getIconFile());
		assertNull(dockingPort.getToolTipText());
		assertNull(dockingPort.getPopupMenu());
		assertTrue(dockingPort.isDragable());
		assertTrue(dockingPort.isFloatable());
		assertTrue(dockingPort.isDropable());
		assertNull(dockingPort.getDockingPort());
		assertEquals(0, dockingPort.getDockableCount());
		assertTrue(dockingPort.canClose());
		assertFalse(dockingPort.getDisposeOnEmpty());
		assertFalse(dockingPort.isDisposed());
	}
	
// Clone ***********************************************************************
	
	@Test
	public void testClone() throws CloneNotSupportedException {
		String testTitle = "Test Title";
		String testIconFile = ImageResources.GLOBE_IMAGE;
		String testToolTipText = "Test ToolTipText";
		JPopupMenu testPopupMenu = new JPopupMenu();
		
		DesktopPaneDockingPort originalPort = new DesktopPaneDockingPort();
		originalPort.setTitle(testTitle);
		originalPort.setIconFile(testIconFile);
		originalPort.setToolTipText(testToolTipText);
		originalPort.setPopupMenu(testPopupMenu);
		originalPort.dock(createTestDockable("Dockable 0"), 0);
		originalPort.dock(createTestDockable("Dockable 1"), 1);
		originalPort.dock(createTestDockable("Dockable 2"), 2);
		
		DesktopPaneDockingPort copyPort = originalPort.clone();
		assertEquals(3, copyPort.getDockableCount());
		
		Dockable firstDockable = copyPort.getDockable(0);
		assertEquals("Dockable 0", firstDockable.getTitle());
		
		Dockable secondDockable = copyPort.getDockable(1);
		assertEquals("Dockable 1", secondDockable.getTitle());
		
		Dockable thirdDockable = copyPort.getDockable(2);
		assertEquals("Dockable 2", thirdDockable.getTitle());
		
		assertEquals(testTitle, copyPort.getTitle());
		assertEquals(testIconFile, copyPort.getIconFile());
		assertEquals(testToolTipText, copyPort.getToolTipText());
		assertTrue(copyPort.getPopupMenu() instanceof JPopupMenu);
		
		originalPort.dispose();
		copyPort.dispose();
	}
	
// Dock/Undock *****************************************************************
	
	@Test
	public void testDockUndock() {
		testDockUndock(0, createTestDockable("Dockable 0"));
	}
	
	private Dockable createTestDockable(String title) {
		BasicDockable basicDockable = 
			new BasicDockable(new JButton(title), title);
		InternalFrameDockingPort port = new InternalFrameDockingPort();
		port.dock(basicDockable, InternalFrameDockingPort.LOCATIONNAME_CONTENT);
		return port;
	}

	// Allow the test to be run with JUnit 3 test runners
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestDesktopPaneDockingPort.class);
	}

}
