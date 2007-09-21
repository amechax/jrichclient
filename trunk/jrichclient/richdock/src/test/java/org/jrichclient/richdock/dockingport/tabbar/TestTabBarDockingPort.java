package org.jrichclient.richdock.dockingport.tabbar;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import static org.jrichclient.richdock.UnitTestUtils.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JPopupMenu;

import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.DockingPortTester;
import org.jrichclient.richdock.dockable.BasicDockable;
import org.jrichclient.richdock.icons.ImageResources;
import org.junit.*;

import junit.framework.JUnit4TestAdapter;

public class TestTabBarDockingPort extends DockingPortTester<Integer> {
	private final TabBarDockingPort dockingPort;
	
// Constructor *****************************************************************
	
	public TestTabBarDockingPort() {
		this(new TabBarDockingPort());
	}
	
	public TestTabBarDockingPort(TabBarDockingPort dockingPort) {
		super(dockingPort);
		
		this.dockingPort = dockingPort;
	}
	
// TestConstructor *************************************************************
	
	@Test
	public void testConstructor1() {
		assertSame(Rotation.ROTATION_0, dockingPort.getRotation());
		assertNull(dockingPort.getSelectedDockable());
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
		TabBarDockingPort dockingPort = new TabBarDockingPort(Rotation.ROTATION_90);
		assertSame(Rotation.ROTATION_90, dockingPort.getRotation());
		assertNull(dockingPort.getSelectedDockable());
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
	
// Clone ***********************************************************************
	
	@Test
	public void testClone() throws CloneNotSupportedException {
		TabBarDockingPort original = new TabBarDockingPort(Rotation.ROTATION_90);
		Dockable originalDockable = createTestDockable("Test Dockable");
		original.dock(originalDockable, Integer.valueOf(0));
		original.setSelectedDockable(originalDockable);
		
		TabBarDockingPort copy = original.clone();
		assertSame(Rotation.ROTATION_90, copy.getRotation());
		assertEquals(originalDockable.getTitle(), copy.getTitle());
		assertEquals(originalDockable.getIconFile(), copy.getIconFile());
		assertEquals(originalDockable.getToolTipText(), copy.getToolTipText());
		assertEquals(1, copy.getDockableCount());
		assertEquals(originalDockable.getTitle(), copy.getSelectedDockable().getTitle());
		
		original.dispose();
		copy.dispose();
	}

	private static Dockable createTestDockable(String title) {
		JButton testContent = new JButton(title);
		String testIconFile = ImageResources.GLOBE_IMAGE;
		String testToolTipText = title + " ToolTipText";
		JPopupMenu testPopupMenu = new JPopupMenu();
		
		Dockable testDockable = new BasicDockable(testContent, title, 
			testIconFile, testToolTipText, testPopupMenu);
		
		return testDockable;
	}

// SelectedDockable ************************************************************
	
	@Test
	public void testSelectedDockable() {
		Dockable oldSelectedDockable = dockingPort.getSelectedDockable();
		Dockable newSelectedDockable = createTestDockable("Test Dockable");
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(
			TabBarDockingPort.PROPERTYNAME_SELECTED_DOCKABLE, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			TabBarDockingPort.PROPERTYNAME_SELECTED_DOCKABLE, 
			oldSelectedDockable, newSelectedDockable));
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			TabBarDockingPort.PROPERTYNAME_SELECTED_DOCKABLE, 
			newSelectedDockable, oldSelectedDockable));
		
		replay(listener);
		dockingPort.dock(newSelectedDockable, dockingPort.getDockableCount());
		
		dockingPort.setSelectedDockable(newSelectedDockable);
		assertSame(newSelectedDockable, dockingPort.getSelectedDockable());
		assertEquals(newSelectedDockable.getTitle(), dockingPort.getTitle());
		assertEquals(newSelectedDockable.getIconFile(), dockingPort.getIconFile());
		assertEquals(newSelectedDockable.getToolTipText(), dockingPort.getToolTipText());
		assertEquals(newSelectedDockable.getPopupMenu(), dockingPort.getPopupMenu());
		
		dockingPort.setSelectedDockable(oldSelectedDockable);
		assertSame(oldSelectedDockable, dockingPort.getSelectedDockable());
		
		dockingPort.undock(newSelectedDockable, false);
		
		verify(listener);
		dockingPort.removePropertyChangeListener(
			TabBarDockingPort.PROPERTYNAME_SELECTED_DOCKABLE, listener);
	}
		
// Dock/Undock *****************************************************************
	
	@Test
	public void testDockUndock() {
		testDockUndock(Integer.valueOf(0));
	}

	// Allow the test to be run with JUnit 3 test runners
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestTabBarDockingPort.class);
	}

}
