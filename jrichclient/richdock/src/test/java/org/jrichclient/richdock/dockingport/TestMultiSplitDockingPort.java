package org.jrichclient.richdock.dockingport;

import static org.junit.Assert.*;

import javax.swing.JButton;
import javax.swing.JPopupMenu;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPortTester;
import org.jrichclient.richdock.dockable.BasicDockable;
import org.jrichclient.richdock.icons.ImageResources;
import org.jrichclient.richdock.multisplitpane.DefaultSplitPaneModel;
import org.jrichclient.richdock.multisplitpane.MultiSplitLayout.Node;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;

public class TestMultiSplitDockingPort extends DockingPortTester<String> {
	private final MultiSplitDockingPort dockingPort;
	
// Constructors ****************************************************************
	
	public TestMultiSplitDockingPort() {
		this(new MultiSplitDockingPort(new DefaultSplitPaneModel()));
	}
	
	public TestMultiSplitDockingPort(MultiSplitDockingPort dockingPort) {
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
		Node testModel = new DefaultSplitPaneModel();
		MultiSplitDockingPort dockingPort = new MultiSplitDockingPort(testModel);
		assertSame(testModel, dockingPort.getModel());
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
	public void testConstructor3() {
		Node testModel = new DefaultSplitPaneModel();
		String testTitle = "Test Title";
		MultiSplitDockingPort dockingPort = new MultiSplitDockingPort(testModel, testTitle);
		assertSame(testModel, dockingPort.getModel());
		assertEquals(testTitle, dockingPort.getTitle());
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
		Node testModel = new DefaultSplitPaneModel();
		String testTitle = "Test Title";
		String testIconFile = ImageResources.GLOBE_IMAGE;
		MultiSplitDockingPort dockingPort = new MultiSplitDockingPort(
			testModel, testTitle, testIconFile);
		assertSame(testModel, dockingPort.getModel());
		assertEquals(testTitle, dockingPort.getTitle());
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
	public void testConstructor5() {
		Node testModel = new DefaultSplitPaneModel();
		String testTitle = "Test Title";
		String testIconFile = ImageResources.GLOBE_IMAGE;
		String testToolTipText = "Test ToolTipText";
		MultiSplitDockingPort dockingPort = new MultiSplitDockingPort(
			testModel, testTitle, testIconFile, testToolTipText);
		assertSame(testModel, dockingPort.getModel());
		assertEquals(testTitle, dockingPort.getTitle());
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
	public void testConstructor6() {
		Node testModel = new DefaultSplitPaneModel();
		String testTitle = "Test Title";
		String testIconFile = ImageResources.GLOBE_IMAGE;
		String testToolTipText = "Test ToolTipText";
		JPopupMenu testPopupMenu = new JPopupMenu();
		MultiSplitDockingPort dockingPort = new MultiSplitDockingPort(
			testModel, testTitle, testIconFile, testToolTipText, testPopupMenu);
		assertSame(testModel, dockingPort.getModel());
		assertEquals(testTitle, dockingPort.getTitle());
		assertEquals(testToolTipText, dockingPort.getToolTipText());
		assertSame(testPopupMenu, dockingPort.getPopupMenu());
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
		String testTitle = "Test MultiSplitDockingPort";
		MultiSplitDockingPort originalPort = new MultiSplitDockingPort(
			new DefaultSplitPaneModel(), testTitle);
		originalPort.dock(createTestDockable("Left"), DefaultSplitPaneModel.LEFT);
		originalPort.dock(createTestDockable("Top"), DefaultSplitPaneModel.TOP);
		originalPort.dock(createTestDockable("Bottom"), DefaultSplitPaneModel.BOTTOM);
		
		MultiSplitDockingPort copyPort = originalPort.clone();
		assertEquals(3, copyPort.getDockableCount());
		
		Dockable leftDockable = copyPort.getDockable(DefaultSplitPaneModel.LEFT);
		assertEquals("Left", leftDockable.getTitle());
		
		Dockable topDockable = copyPort.getDockable(DefaultSplitPaneModel.TOP);
		assertEquals("Top", topDockable.getTitle());
		
		Dockable bottomDockable = copyPort.getDockable(DefaultSplitPaneModel.BOTTOM);
		assertEquals("Bottom", bottomDockable.getTitle());
		
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
		testDockUndock(DefaultSplitPaneModel.LEFT);
		testDockUndock(DefaultSplitPaneModel.TOP);
		testDockUndock(DefaultSplitPaneModel.BOTTOM);
	}

	// Allow the test to be run with JUnit 3 test runners
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestMultiSplitDockingPort.class);
	}

}
