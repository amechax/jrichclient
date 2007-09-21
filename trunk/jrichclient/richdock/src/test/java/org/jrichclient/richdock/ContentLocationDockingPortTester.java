package org.jrichclient.richdock;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import static org.jrichclient.richdock.UnitTestUtils.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPopupMenu;

import org.jrichclient.richdock.dockable.BasicDockable;
import org.jrichclient.richdock.icons.ImageResources;
import org.junit.Test;

public class ContentLocationDockingPortTester extends DockingPortTester<String> {
	private final DockingPort<String> dockingPort;
	private final String contentLocation;

	public ContentLocationDockingPortTester(DockingPort<String> dockingPort, String contentLocation) {
		super(dockingPort);
		
		this.dockingPort = dockingPort;
		this.contentLocation = contentLocation;
	}
	
// Title ***********************************************************************
	
	@Test
	public void testSyncDockTitle() {
		String testTitle1 = "Test Title1";
		Dockable testDockable = new BasicDockable();
		testDockable.setTitle(testTitle1);
		String testTitle2 = "Test Title2";
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(Dockable.PROPERTYNAME_TITLE, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			Dockable.PROPERTYNAME_TITLE, "", testTitle1));
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			Dockable.PROPERTYNAME_TITLE, testTitle1, testTitle2));
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			Dockable.PROPERTYNAME_TITLE, testTitle2, ""));
		
		replay(listener);
		assertEquals("", dockingPort.getTitle());
		
		dockingPort.dock(testDockable, contentLocation);
		assertSame(testTitle1, dockingPort.getTitle());
		
		testDockable.setTitle(testTitle2);
		assertSame(testTitle2, dockingPort.getTitle());
		
		dockingPort.undock(testDockable, false);
		assertEquals("", dockingPort.getTitle());
		
		verify(listener);
		dockingPort.removePropertyChangeListener(Dockable.PROPERTYNAME_TITLE, listener);
	}
	
// IconImage *******************************************************************
	
	@Test
	public void testSyncDockIconFile() {
		String testIconFile1 = ImageResources.GLOBE_IMAGE;
		Dockable testDockable = new BasicDockable();
		testDockable.setIconFile(testIconFile1);
		String testIconFile2 = ImageResources.CLOSE_IMAGE;
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(Dockable.PROPERTYNAME_ICON_FILE, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			Dockable.PROPERTYNAME_ICON_FILE, null, testIconFile1));
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			Dockable.PROPERTYNAME_ICON_FILE, testIconFile1, testIconFile2));
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			Dockable.PROPERTYNAME_ICON_FILE, testIconFile2, null));
		
		replay(listener);
		assertNull(dockingPort.getIconFile());
		
		dockingPort.dock(testDockable, contentLocation);
		assertSame(testIconFile1, dockingPort.getIconFile());
		
		testDockable.setIconFile(testIconFile2);
		assertSame(testIconFile2, dockingPort.getIconFile());
		
		dockingPort.undock(testDockable, false);
		assertNull(dockingPort.getIconFile());
		verify(listener);
		dockingPort.removePropertyChangeListener(Dockable.PROPERTYNAME_ICON_FILE, listener);
	}
	
// ToolTipText *****************************************************************
	
	@Test
	public void testSyncDockToolTipText() {
		String testToolTipText1 = "Test ToolTipText 1";
		Dockable testDockable = new BasicDockable();
		testDockable.setToolTipText(testToolTipText1);
		String testToolTipText2 = "Test ToolTipText 2";
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(Dockable.PROPERTYNAME_TOOL_TIP_TEXT, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			Dockable.PROPERTYNAME_TOOL_TIP_TEXT, null, testToolTipText1));
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			Dockable.PROPERTYNAME_TOOL_TIP_TEXT, testToolTipText1, testToolTipText2));
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			Dockable.PROPERTYNAME_TOOL_TIP_TEXT, testToolTipText2, null));
		
		replay(listener);
		assertNull(dockingPort.getToolTipText());
		
		dockingPort.dock(testDockable, contentLocation);
		assertSame(testToolTipText1, dockingPort.getToolTipText());
		
		testDockable.setToolTipText(testToolTipText2);
		assertSame(testToolTipText2, dockingPort.getToolTipText());
		
		dockingPort.undock(testDockable, false);
		assertNull(dockingPort.getToolTipText());
		verify(listener);
		dockingPort.removePropertyChangeListener(Dockable.PROPERTYNAME_TOOL_TIP_TEXT, listener);
	}
	
// PopupMenu *******************************************************************
	
	@Test
	public void testSyncDockPopupMenu() {
		JPopupMenu testPopupMenu1 = new JPopupMenu();
		Dockable testDockable = new BasicDockable();
		testDockable.setPopupMenu(testPopupMenu1);
		JPopupMenu testPopupMenu2 = new JPopupMenu();
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockingPort.addPropertyChangeListener(Dockable.PROPERTYNAME_POPUP_MENU, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			Dockable.PROPERTYNAME_POPUP_MENU, null, testPopupMenu1));
		propertyChange(listener, new PropertyChangeEvent(dockingPort, 
			Dockable.PROPERTYNAME_POPUP_MENU, testPopupMenu1, testPopupMenu2));
		propertyChange(listener, new PropertyChangeEvent(dockingPort,
			Dockable.PROPERTYNAME_POPUP_MENU, testPopupMenu2, null));
		
		replay(listener);
		assertNull(dockingPort.getPopupMenu());
		
		dockingPort.dock(testDockable, contentLocation);
		assertSame(testPopupMenu1, dockingPort.getPopupMenu());
		
		testDockable.setPopupMenu(testPopupMenu2);
		assertSame(testPopupMenu2, dockingPort.getPopupMenu());
		
		dockingPort.undock(testDockable, false);
		assertNull(dockingPort.getPopupMenu());
		verify(listener);
		dockingPort.removePropertyChangeListener(Dockable.PROPERTYNAME_POPUP_MENU, listener);
	}

}
