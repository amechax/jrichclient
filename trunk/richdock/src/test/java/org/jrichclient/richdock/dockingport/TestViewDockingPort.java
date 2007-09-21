package org.jrichclient.richdock.dockingport;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import static org.jrichclient.richdock.UnitTestUtils.*;

import java.beans.ExceptionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;

import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;

import org.jrichclient.richdock.ContentLocationDockingPortTester;
import org.jrichclient.richdock.Dockable;
import org.jrichclient.richdock.dockable.BasicDockable;
import org.jrichclient.richdock.icons.ImageResources;
import org.jrichclient.richdock.utils.ActionUtils;

import org.junit.Test;

import junit.framework.JUnit4TestAdapter;

public class TestViewDockingPort extends ContentLocationDockingPortTester {
	private final ViewDockingPort dockingPort;
	
// Constructors ****************************************************************
	
	public TestViewDockingPort() {
		this(new ViewDockingPort());
	}
	
	public TestViewDockingPort(ViewDockingPort dockingPort) {
		super(dockingPort, ViewDockingPort.LOCATIONNAME_CONTENT);
		
		this.dockingPort = dockingPort;
	}
	
// Test Constructors ***********************************************************
	
	@Test
	public void testConstructor() {
		assertEquals("", dockingPort.getTitle());
		assertNull(dockingPort.getIconFile());
		assertNull(dockingPort.getToolTipText());
		assertNull(dockingPort.getPopupMenu());
		assertTrue(dockingPort.isDragable());
		assertTrue(dockingPort.isFloatable());
		assertFalse(dockingPort.isDropable());
		assertNull(dockingPort.getToolBar());
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
		
		BasicDockable originalDockable = new BasicDockable(testContent,
			testTitle, testIconFile, testToolTipText);
		originalDockable.setPopupMenu(ActionUtils.createClosePopupMenu(originalDockable));
		
		ViewDockingPort original = new ViewDockingPort();
		original.dock(originalDockable, ViewDockingPort.LOCATIONNAME_CONTENT);
		original.setToolBar(ActionUtils.createCloseToolBar(original));
		
		ViewDockingPort copy = original.clone();
		assertEquals(1, copy.getDockableCount());
		
		Dockable contentDockable = copy.getDockable(ViewDockingPort.LOCATIONNAME_CONTENT);
		assertEquals(testTitle, contentDockable.getTitle());
		assertEquals(testIconFile, contentDockable.getIconFile());
		assertEquals(testToolTipText, contentDockable.getToolTipText());
		assertTrue(contentDockable.getPopupMenu() instanceof JPopupMenu);
		
		assertEquals(testTitle, copy.getTitle());
		assertEquals(testIconFile, copy.getIconFile());
		assertEquals(testToolTipText, copy.getToolTipText());
		assertTrue(copy.getPopupMenu() instanceof JPopupMenu);
		
		original.dispose();
		copy.dispose();
	}
	
// Encode JToolBar *************************************************************
	
	@Test
	public void testEncodeToolBar() {
		JToolBar toolBar = new JToolBar();
		//toolBar.add(new JButton());
		
		ByteArrayOutputStream testStream = new ByteArrayOutputStream();
		XMLEncoder encoder = new XMLEncoder(testStream);
		encoder.setExceptionListener(new ExceptionListener() {
			public void exceptionThrown(Exception ex) {
				ex.printStackTrace();
			}
		});
		encoder.writeObject(toolBar);
		encoder.close();
		//System.out.println(testStream.toString());
	}
	
// ToolBar *********************************************************************
	
	@Test
	public void testToolBar() {
		JToolBar oldToolBar = dockingPort.getToolBar();
		JToolBar newToolBar = new JToolBar("Test ToolBar");
	
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(ViewDockingPort.PROPERTYNAME_TOOL_BAR, listener);
	
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			ViewDockingPort.PROPERTYNAME_TOOL_BAR, oldToolBar, newToolBar));
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			ViewDockingPort.PROPERTYNAME_TOOL_BAR, newToolBar, oldToolBar));
	
		replay(listener);
		dockingPort.setToolBar(newToolBar);
		assertSame(newToolBar, dockingPort.getToolBar());
	
		dockingPort.setToolBar(oldToolBar);
		assertSame(oldToolBar, dockingPort.getToolBar());
	
		dockingPort.removePropertyChangeListener(ViewDockingPort.PROPERTYNAME_TOOL_BAR, listener);
		verify(listener);
	}
	
// Dock/Undock *****************************************************************
	
	@Test
	public void testDockUndock() {
		testDockUndock(ViewDockingPort.LOCATIONNAME_CONTENT);
	}
		
	// Allow the test to be run with JUnit 3 test runners
	public static junit.framework.Test suite() {
		return new JUnit4TestAdapter(TestViewDockingPort.class);
	}

}
