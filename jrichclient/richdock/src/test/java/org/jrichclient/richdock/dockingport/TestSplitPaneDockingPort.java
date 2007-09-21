package org.jrichclient.richdock.dockingport;

import static org.junit.Assert.*;

import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPortTester;
import org.jrichclient.richdock.dockable.BasicDockable;
import org.jrichclient.richdock.icons.ImageResources;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;

public class TestSplitPaneDockingPort extends DockingPortTester<String> {
	private final SplitPaneDockingPort dockingPort;
	
// Constructors ****************************************************************
	
	public TestSplitPaneDockingPort() {
		this(new SplitPaneDockingPort());
	}
	
	public TestSplitPaneDockingPort(SplitPaneDockingPort dockingPort) {
		super(dockingPort);
		
		this.dockingPort = dockingPort;
	}
	
// Test Constructors ***********************************************************
	
	@Test
	public void testConstructor1() {
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
	
	@Test
	public void testConstructor2() {
		String testTitle = "Test Title";
		SplitPaneDockingPort dockingPort = new SplitPaneDockingPort(testTitle);
		assertEquals(testTitle, dockingPort.getTitle());
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
	
	@Test
	public void testConstructor3() {
		String testTitle = "Test Title";
		String testIconFile = ImageResources.GLOBE_IMAGE;
		SplitPaneDockingPort dockingPort = new SplitPaneDockingPort(testTitle, testIconFile);
		assertEquals(testTitle, dockingPort.getTitle());
		assertEquals(testIconFile, dockingPort.getIconFile());
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
	
	@Test
	public void testConstructor4() {
		String testTitle = "Test Title";
		String testIconFile = ImageResources.GLOBE_IMAGE;
		String testToolTipText = "Test ToolTipText";
		SplitPaneDockingPort dockingPort = new SplitPaneDockingPort(testTitle, 
			testIconFile, testToolTipText);
		assertEquals(testTitle, dockingPort.getTitle());
		assertEquals(testIconFile, dockingPort.getIconFile());
		assertEquals(testToolTipText, dockingPort.getToolTipText());
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
	
	@Test
	public void testConstructor5() {
		String testTitle = "Test Title";
		String testIconFile = ImageResources.GLOBE_IMAGE;
		String testToolTipText = "Test ToolTipText";
		JPopupMenu testPopupMenu = new JPopupMenu();
		SplitPaneDockingPort dockingPort = new SplitPaneDockingPort(testTitle,
			testIconFile, testToolTipText, testPopupMenu);
		assertEquals(testTitle, dockingPort.getTitle());
		assertEquals(testIconFile, dockingPort.getIconFile());
		assertEquals(testToolTipText, dockingPort.getToolTipText());
		assertEquals(testPopupMenu, dockingPort.getPopupMenu());
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
		String testTitle = "Test SplitPaneDockingPort";
		SplitPaneDockingPort originalPort = new SplitPaneDockingPort(testTitle);
		originalPort.dock(createTestDockable("Left"), JSplitPane.LEFT);
		originalPort.dock(createTestDockable("Right"), JSplitPane.RIGHT);
		
		SplitPaneDockingPort copyPort = originalPort.clone();
		assertEquals(2, copyPort.getDockableCount());
		
		Dockable leftDockable = copyPort.getDockable(JSplitPane.LEFT);
		assertEquals("Left", leftDockable.getTitle());
		
		Dockable rightDockable = copyPort.getDockable(JSplitPane.RIGHT);
		assertEquals("Right", rightDockable.getTitle());
		
		assertEquals(testTitle, copyPort.getTitle());
		
		originalPort.dispose();
		copyPort.dispose();
	}
	
	private Dockable createTestDockable(String title) {
		JButton testContent = new JButton(title);
		String testIconFile = ImageResources.GLOBE_IMAGE;
		String testToolTipText = title + " ToolTipText";
		JPopupMenu testPopupMenu = new JPopupMenu();
		
		Dockable testDockable = new BasicDockable(testContent, title, 
			testIconFile, testToolTipText, testPopupMenu);
		
		return testDockable;
	}
	
// Dock/Undock *****************************************************************
	
	@Test
	public void testDockUndock() {
		testDockUndock(JSplitPane.LEFT);
		testDockUndock(JSplitPane.RIGHT);
		testDockUndock(JSplitPane.TOP);
		testDockUndock(JSplitPane.BOTTOM);
	}

	// Allow the test to be run with JUnit 3 test runners
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestSplitPaneDockingPort.class);
	}

}
