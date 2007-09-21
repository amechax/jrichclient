package org.jrichclient.richdock.dockingport;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import static org.jrichclient.richdock.UnitTestUtils.*;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JPopupMenu;

import junit.framework.JUnit4TestAdapter;

import org.jrichclient.richdock.ContentLocationDockingPortTester;
import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.dockable.BasicDockable;
import org.jrichclient.richdock.icons.ImageResources;
import org.junit.*;

public class TestBorderLayoutDockingPort extends ContentLocationDockingPortTester {
	private final BorderLayoutDockingPort dockingPort;
	
// Constructor *****************************************************************
	
	public TestBorderLayoutDockingPort() {
		this(new BorderLayoutDockingPort());
	}

	public TestBorderLayoutDockingPort(BorderLayoutDockingPort dockingPort) {
		super(dockingPort, BorderLayout.CENTER);
		
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
		assertEquals(0, dockingPort.getHgap());
		assertEquals(0, dockingPort.getVgap());
		assertEquals(0, dockingPort.getDockableCount());
		assertTrue(dockingPort.canClose());
		assertFalse(dockingPort.getDisposeOnEmpty());
		assertFalse(dockingPort.isDisposed());
	}
	
// Clone ***********************************************************************
	
	@Test
	public void testClone() throws CloneNotSupportedException {
		BorderLayoutDockingPort originalPort = new BorderLayoutDockingPort();
		originalPort.setVgap(10);
		originalPort.setHgap(5);
		originalPort.dock(createTestDockable("Center"), BorderLayout.CENTER);
		originalPort.dock(createTestDockable("North"), BorderLayout.NORTH);
		originalPort.dock(createTestDockable("South"), BorderLayout.SOUTH);
		originalPort.dock(createTestDockable("East"), BorderLayout.EAST);
		originalPort.dock(createTestDockable("West"), BorderLayout.WEST);
		
		BorderLayoutDockingPort copyPort = originalPort.clone();
		assertEquals(10, copyPort.getVgap());
		assertEquals(5, copyPort.getHgap());

		assertEquals(5, copyPort.getDockableCount());
		
		Dockable centerDockable = copyPort.getDockable(BorderLayout.CENTER);
		assertEquals("Center", centerDockable.getTitle());
		
		Dockable northDockable = copyPort.getDockable(BorderLayout.NORTH);
		assertEquals("North", northDockable.getTitle());
		
		Dockable southDockable = copyPort.getDockable(BorderLayout.SOUTH);
		assertEquals("South", southDockable.getTitle());
		
		Dockable eastDockable = copyPort.getDockable(BorderLayout.EAST);
		assertEquals("East", eastDockable.getTitle());
		
		Dockable westDockable = copyPort.getDockable(BorderLayout.WEST);
		assertEquals("West", westDockable.getTitle());
		
		assertEquals("Center", copyPort.getTitle());
		
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
	
// Hgap ************************************************************************
	
	@Test
	public void testHgap() {
		int oldHgap = dockingPort.getHgap();
		int newHgap = oldHgap + 5;
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(
			BorderLayoutDockingPort.PROPERTYNAME_HGAP, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			BorderLayoutDockingPort.PROPERTYNAME_HGAP, oldHgap, newHgap));
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			BorderLayoutDockingPort.PROPERTYNAME_HGAP, newHgap, oldHgap));
		
		replay(listener);
		dockingPort.setHgap(newHgap);
		assertSame(newHgap, dockingPort.getHgap());
		
		dockingPort.setHgap(oldHgap);
		assertSame(oldHgap, dockingPort.getHgap());
		
		dockingPort.removePropertyChangeListener(
			BorderLayoutDockingPort.PROPERTYNAME_HGAP, listener);
		verify(listener);
	}
	
// Vgap ************************************************************************
	
	@Test
	public void testVgap() {
		int oldVgap = dockingPort.getVgap();
		int newVgap = oldVgap + 5;
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(
				BorderLayoutDockingPort.PROPERTYNAME_VGAP, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			BorderLayoutDockingPort.PROPERTYNAME_VGAP, oldVgap, newVgap));
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			BorderLayoutDockingPort.PROPERTYNAME_VGAP, newVgap, oldVgap));
		
		replay(listener);
		dockingPort.setVgap(newVgap);
		assertSame(newVgap, dockingPort.getVgap());
		
		dockingPort.setVgap(oldVgap);
		assertSame(oldVgap, dockingPort.getVgap());
		
		dockingPort.removePropertyChangeListener(
			BorderLayoutDockingPort.PROPERTYNAME_VGAP, listener);
		verify(listener);
	}
		
// Dock/Undock *****************************************************************
	
	@Test
	public void testDockUndock() {
		testDockUndock(BorderLayout.CENTER);
		testDockUndock(BorderLayout.NORTH);
		testDockUndock(BorderLayout.SOUTH);
		testDockUndock(BorderLayout.EAST);
		testDockUndock(BorderLayout.WEST);
	}
	
	// Allow the test to be run with JUnit 3 test runners
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestBorderLayoutDockingPort.class);
	}
}
