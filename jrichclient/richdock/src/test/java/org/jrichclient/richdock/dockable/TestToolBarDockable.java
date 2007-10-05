package org.jrichclient.richdock.dockable;

import static org.junit.Assert.*;

import javax.swing.JPopupMenu;
import javax.swing.JToolBar;

import junit.framework.JUnit4TestAdapter;

import org.jrichclient.richdock.DockableTester;
import org.jrichclient.richdock.icons.ImageResources;
import org.junit.*;

public class TestToolBarDockable extends DockableTester {
	private final ToolBarDockable dockable;
	
// Constructors ****************************************************************
	
	public TestToolBarDockable() {
		this(new ToolBarDockable(new JToolBar()));
	}
	
	public TestToolBarDockable(ToolBarDockable dockable) {
		super(dockable);
		
		this.dockable = dockable;
	}
	
// Test Constructor ************************************************************
	
	@Test
	public void testConstructor1() {
		assertNull(dockable.getTitle());
		assertNull(dockable.getIconFile());
		assertNull(dockable.getToolTipText());
		assertNull(dockable.getPopupMenu());
		assertTrue(dockable.isDragable());
		assertTrue(dockable.isFloatable());
		assertNull(dockable.getDockingPort());
		assertNotNull(dockable.getComponent());
		assertTrue(dockable.canClose());
		assertFalse(dockable.isDisposed());
	}
	
	@Test
	public void testConstructor2() {
		String testTitle = "Test Dockable";
		JToolBar toolBar = new JToolBar(testTitle);
		
		ToolBarDockable testDockable = new ToolBarDockable(toolBar);
		assertEquals(testTitle, testDockable.getTitle());
		assertNull(testDockable.getIconFile());
		assertNull(testDockable.getToolTipText());
		assertNull(testDockable.getPopupMenu());
		assertTrue(testDockable.isDragable());
		assertTrue(testDockable.isFloatable());
		assertNull(testDockable.getDockingPort());
		assertNotNull(testDockable.getComponent());
		assertTrue(testDockable.canClose());
		assertFalse(testDockable.isDisposed());
	}
	
// Clone ***********************************************************************
	
	@Test
	public void testClone() {
		try {
			String testTitle = "Test ToolBar";
			JToolBar toolBar = new JToolBar(testTitle);
			toolBar.addSeparator();
			int compCount = toolBar.getComponentCount();
			
			ToolBarDockable original = new ToolBarDockable(toolBar);
			original.setIconFile(ImageResources.GLOBE_IMAGE);
			original.setToolTipText("Test ToolTip Text");
			original.setPopupMenu(new JPopupMenu());
			
			ToolBarDockable copy = original.clone();
			assertEquals(compCount, copy.getComponent().getComponentCount());
			assertEquals(original.getTitle(), copy.getTitle());
			assertEquals(original.getIconFile(), copy.getIconFile());
			assertEquals(original.getToolTipText(), copy.getToolTipText());
			assertTrue(copy.getPopupMenu() instanceof JPopupMenu);
			assertEquals(original.isDragable(), copy.isDragable());
			assertEquals(original.isFloatable(), copy.isFloatable());
			assertEquals(original.getDockingPort(), copy.getDockingPort());
			assertNotNull(copy.getComponent());
			assertFalse(copy.isDisposed());
			
			original.dispose();
			copy.dispose();
		} catch (CloneNotSupportedException ex) {
			fail("Clone not supported");
		}
	}
	
	// Allow the test to be run with JUnit 3 test runners
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestToolBarDockable.class);
	}

}
