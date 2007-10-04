package org.jrichclient.richdock;

import static org.jrichclient.richdock.UnitTestUtils.*;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPopupMenu;

import org.jrichclient.richdock.icons.ImageResources;
import org.junit.After;
import org.junit.Test;

public class DockableTester {
	private final Dockable dockable;
	
// Constructor *****************************************************************
	
	public DockableTester(Dockable dockable) {
		this.dockable = dockable;
	}
		
// Title ***********************************************************************
	
	@Test
	public void testTitle() {
		String oldTitle = dockable.getTitle();
		String newTitle = "New " + oldTitle;
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockable.addPropertyChangeListener(Dockable.PROPERTYNAME_TITLE, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockable, 
			Dockable.PROPERTYNAME_TITLE, oldTitle, newTitle));
		propertyChange(listener, new PropertyChangeEvent(dockable, 
			Dockable.PROPERTYNAME_TITLE, newTitle, oldTitle));
		
		replay(listener);
		dockable.setTitle(newTitle);
		assertSame(newTitle, dockable.getTitle());
		
		dockable.setTitle(oldTitle);
		assertSame(oldTitle, dockable.getTitle());
		
		dockable.removePropertyChangeListener(Dockable.PROPERTYNAME_TITLE, listener);
		verify(listener);
	}
	
// IconImage *******************************************************************
	
	@Test
	public void testIconFile() {
		String oldIconFile = dockable.getIconFile();
		String newIconFile = ImageResources.GLOBE_IMAGE;
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockable.addPropertyChangeListener(Dockable.PROPERTYNAME_ICON_FILE, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockable, 
			Dockable.PROPERTYNAME_ICON_FILE, oldIconFile, newIconFile));
		propertyChange(listener, new PropertyChangeEvent(dockable, 
			Dockable.PROPERTYNAME_ICON_FILE, newIconFile, oldIconFile));
		
		replay(listener);
		dockable.setIconFile(newIconFile);
		assertSame(newIconFile, dockable.getIconFile());
		
		dockable.setIconFile(oldIconFile);
		assertSame(oldIconFile, dockable.getIconFile());
		
		dockable.removePropertyChangeListener(Dockable.PROPERTYNAME_ICON_FILE, listener);
		verify(listener);
	}
	
// ToolTipText *****************************************************************
	
	@Test
	public void testToolTipText() {
		String oldToolTipText = dockable.getToolTipText();
		String newToolTipText = "Test ToolTipText";
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockable.addPropertyChangeListener(Dockable.PROPERTYNAME_TOOL_TIP_TEXT, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockable, 
			Dockable.PROPERTYNAME_TOOL_TIP_TEXT, oldToolTipText, newToolTipText));
		propertyChange(listener, new PropertyChangeEvent(dockable, 
			Dockable.PROPERTYNAME_TOOL_TIP_TEXT, newToolTipText, oldToolTipText));
		
		replay(listener);
		
		dockable.setToolTipText(newToolTipText);
		assertSame(newToolTipText, dockable.getToolTipText());
		
		dockable.setToolTipText(oldToolTipText);
		assertSame(oldToolTipText, dockable.getToolTipText());
		
		dockable.removePropertyChangeListener(Dockable.PROPERTYNAME_TOOL_TIP_TEXT, listener);
		verify(listener);
	}
	
// PopupMenu *******************************************************************
	
	@Test
	public void testPopupMenu() {
		JPopupMenu oldPopupMenu = dockable.getPopupMenu();
		JPopupMenu newPopupMenu = new JPopupMenu();
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockable.addPropertyChangeListener(Dockable.PROPERTYNAME_POPUP_MENU, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockable, 
			Dockable.PROPERTYNAME_POPUP_MENU, oldPopupMenu, newPopupMenu));
		propertyChange(listener, new PropertyChangeEvent(dockable, 
			Dockable.PROPERTYNAME_POPUP_MENU, newPopupMenu, oldPopupMenu));
		
		replay(listener);
		dockable.setPopupMenu(newPopupMenu);
		assertSame(newPopupMenu, dockable.getPopupMenu());
		
		dockable.setPopupMenu(oldPopupMenu);
		assertSame(oldPopupMenu, dockable.getPopupMenu());
		
		dockable.removePropertyChangeListener(Dockable.PROPERTYNAME_POPUP_MENU, listener);
		verify(listener);
	}
	
// Dragable ********************************************************************
	
	@Test
	public void testDragable() {
		boolean oldDragable = dockable.isDragable();
		boolean newDragable = !oldDragable;
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockable.addPropertyChangeListener(Dockable.PROPERTYNAME_DRAGABLE, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockable,
			Dockable.PROPERTYNAME_DRAGABLE, oldDragable, newDragable));
		propertyChange(listener, new PropertyChangeEvent(dockable,
			Dockable.PROPERTYNAME_DRAGABLE, newDragable, oldDragable));
		
		replay(listener);
		dockable.setDragable(newDragable);
		assertSame(newDragable, dockable.isDragable());
		
		dockable.setDragable(oldDragable);
		assertSame(oldDragable, dockable.isDragable());
		
		dockable.removePropertyChangeListener(Dockable.PROPERTYNAME_DRAGABLE, listener);
		verify(listener);
	}
	
// Floatable *******************************************************************
	
	@Test
	public void testFloatable() {
		boolean oldFloatable = dockable.isFloatable();
		boolean newFloatable = !oldFloatable;
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockable.addPropertyChangeListener(Dockable.PROPERTYNAME_FLOATABLE, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockable,
			Dockable.PROPERTYNAME_FLOATABLE, oldFloatable, newFloatable));
		propertyChange(listener, new PropertyChangeEvent(dockable,
			Dockable.PROPERTYNAME_FLOATABLE, newFloatable, oldFloatable));
		
		replay(listener);
		dockable.setFloatable(newFloatable);
		assertSame(newFloatable, dockable.isFloatable());
		
		dockable.setFloatable(oldFloatable);
		assertSame(oldFloatable, dockable.isFloatable());
		
		dockable.removePropertyChangeListener(Dockable.PROPERTYNAME_FLOATABLE, listener);
		verify(listener);
	}
	
// DockingPort *****************************************************************
	
	@Test
	public void testDockingPort() {
		DockingPort<?> oldDockingPort = dockable.getDockingPort();
		DockingPort<?> newDockingPort = createStrictMock(DockingPort.class);
		
		PropertyChangeListener listener = createStrictMock(PropertyChangeListener.class);
		dockable.addPropertyChangeListener(Dockable.PROPERTYNAME_DOCKING_PORT, listener);
		
		propertyChange(listener, new PropertyChangeEvent(dockable,
			Dockable.PROPERTYNAME_DOCKING_PORT, oldDockingPort, newDockingPort));
		propertyChange(listener, new PropertyChangeEvent(dockable,
			Dockable.PROPERTYNAME_DOCKING_PORT, newDockingPort, oldDockingPort));
		
		replay(listener);
		dockable.setDockingPort(newDockingPort);
		assertSame(newDockingPort, dockable.getDockingPort());
		
		dockable.setDockingPort(oldDockingPort);
		assertSame(oldDockingPort, dockable.getDockingPort());
		
		dockable.removePropertyChangeListener(Dockable.PROPERTYNAME_DOCKING_PORT, listener);
		verify(listener);
	}
	
// Dispose *********************************************************************
	
	@After
	public void tearDown() {
		testDispose(dockable);
	}
	
	public void testDispose(Dockable dockable) {
		dockable.dispose();
		assertTrue(dockable.isDisposed());
	}
	
}
