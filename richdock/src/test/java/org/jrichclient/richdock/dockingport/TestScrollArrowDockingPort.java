package org.jrichclient.richdock.dockingport;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import static org.jrichclient.richdock.UnitTestUtils.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JPopupMenu;

import org.jrichclient.richdock.ContentLocationDockingPortTester;
import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.dockable.BasicDockable;
import org.jrichclient.richdock.icons.ImageResources;
import org.junit.Test;

import junit.framework.JUnit4TestAdapter;

public class TestScrollArrowDockingPort extends ContentLocationDockingPortTester {
	private final ScrollArrowDockingPort dockingPort;

// Constructors ****************************************************************
	
	public TestScrollArrowDockingPort() {
		this(new ScrollArrowDockingPort());
	}
	
	public TestScrollArrowDockingPort(ScrollArrowDockingPort dockingPort) {
		super(dockingPort, ScrollArrowDockingPort.LOCATIONNAME_CONTENT);
		
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
		
		ScrollArrowDockingPort original = new ScrollArrowDockingPort();
		original.dock(originalDockable, ScrollArrowDockingPort.LOCATIONNAME_CONTENT);
		original.setHorizontalArrowPolicy(ScrollArrowDockingPort.HORIZONTAL_ARROW_ALWAYS);
		original.setVerticalArrowPolicy(ScrollArrowDockingPort.VERTICAL_ARROW_NEVER);
		assertEquals(1, original.getDockableCount());
		
		ScrollArrowDockingPort copy = original.clone();
		assertEquals(testTitle, copy.getTitle());
		assertEquals(testIconFile, copy.getIconFile());
		assertEquals(testToolTipText, copy.getToolTipText());
		assertTrue(copy.getPopupMenu() instanceof JPopupMenu);
		assertEquals(ScrollArrowDockingPort.HORIZONTAL_ARROW_ALWAYS, copy.getHorizontalArrowPolicy());
		assertEquals(ScrollArrowDockingPort.VERTICAL_ARROW_NEVER, copy.getVerticalArrowPolicy());
		
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
	
// HorizontalArrowPolicy *******************************************************
	
	@Test
	public void testHorizontalArrowPolicy() {
		int oldHap = dockingPort.getHorizontalArrowPolicy();
		int newHap = (oldHap + 1) % 3;
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(
			ScrollArrowDockingPort.PROPERTYNAME_HORIZONTAL_ARROW_POLICY, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			ScrollArrowDockingPort.PROPERTYNAME_HORIZONTAL_ARROW_POLICY, oldHap, newHap));
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			ScrollArrowDockingPort.PROPERTYNAME_HORIZONTAL_ARROW_POLICY, newHap, oldHap));
		
		replay(listener);
		dockingPort.setHorizontalArrowPolicy(newHap);
		assertEquals(newHap, dockingPort.getHorizontalArrowPolicy());
		
		dockingPort.setHorizontalArrowPolicy(oldHap);
		assertEquals(oldHap, dockingPort.getHorizontalArrowPolicy());
		
		verify(listener);
		dockingPort.removePropertyChangeListener(
			ScrollArrowDockingPort.PROPERTYNAME_HORIZONTAL_ARROW_POLICY, listener);
	}
	
// VerticalArrowPolicy *********************************************************
	
	@Test
	public void testVerticalArrowPolicy() {
		int oldVap = dockingPort.getVerticalArrowPolicy();
		int newVap = (oldVap + 1) % 3;
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(
			ScrollArrowDockingPort.PROPERTYNAME_VERTICAL_ARROW_POLICY, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			ScrollArrowDockingPort.PROPERTYNAME_VERTICAL_ARROW_POLICY, oldVap, newVap));
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			ScrollArrowDockingPort.PROPERTYNAME_VERTICAL_ARROW_POLICY, newVap, oldVap));
		
		replay(listener);
		dockingPort.setVerticalArrowPolicy(newVap);
		assertEquals(newVap, dockingPort.getVerticalArrowPolicy());
		
		dockingPort.setVerticalArrowPolicy(oldVap);
		assertEquals(oldVap, dockingPort.getVerticalArrowPolicy());
		
		verify(listener);
		dockingPort.removePropertyChangeListener(
			ScrollArrowDockingPort.PROPERTYNAME_VERTICAL_ARROW_POLICY, listener);
	}
	
// Dock/Undock *****************************************************************
	
	@Test
	public void testDockUndock() {
		testDockUndock(ScrollArrowDockingPort.LOCATIONNAME_CONTENT);
	}
	
	// Allow the test to be run with JUnit 3 test runners
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestScrollArrowDockingPort.class);
	}

}
